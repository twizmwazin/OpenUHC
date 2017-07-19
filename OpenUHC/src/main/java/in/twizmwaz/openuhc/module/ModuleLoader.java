package in.twizmwaz.openuhc.module;

import in.twizmwaz.openuhc.OpenUHC;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import lombok.NonNull;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;


public class ModuleLoader {

  private static final String MODULE_DESCRIPTOR = Type.getDescriptor(ModuleInfo.class);

  final Set<Class<? extends Module>> moduleEntries = new HashSet<>();

  /**
   * Finds all modules in a loaded jar.
   *
   * @param file The jar file to extract modules from.
   */
  @SuppressWarnings("unchecked")
  public void findEntries(@NonNull File file) {
    OpenUHC.getInstance().getLogger().info("Loading modules from " + file.toString());
    final Set<String> classStrings = new HashSet<>();
    // Jar to load modules from
    try {
      final ZipFile zip = new ZipFile(file);
      final Enumeration<? extends ZipEntry> entries = zip.entries();
      // Loop over entries
      while (entries.hasMoreElements()) {
        ZipEntry entry = entries.nextElement();
        // Skip non-class files
        if (entry.isDirectory() || !entry.getName().endsWith(".class")) {
          continue;
        }
        try {
          final InputStream in = zip.getInputStream(entry);
          // Parse the class file
          final ClassReader reader = new ClassReader(in);
          final ClassNode node = new ClassNode();
          // Skip parts we don't care about
          reader.accept(node, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
          // Loop over annotations
          if (node.visibleAnnotations != null) {
            node.visibleAnnotations.forEach(annotation -> {
              if (((AnnotationNode) annotation).desc.equalsIgnoreCase(MODULE_DESCRIPTOR)) {
                classStrings.add(node.name.replace('/', '.'));
              }
            });
          }
        } catch (IOException e) {
          continue;
        }
      }
    } catch (IOException e) {
      OpenUHC.getInstance().getLogger().severe("Unable to find resource at " + file.toString());
      return;
    }
    // Now that we have located ModuleEntries, for each
    classStrings.forEach(classString -> {
      try {
        // Get the class from the name ASM found
        final Class clazz = Class.forName(classString);
        // And save it for later
        moduleEntries.add(clazz);
      } catch (ClassNotFoundException ex) {
        OpenUHC.getInstance().getLogger().warning("ASM found module '" + classString
            + "' but it could not be located, skipping.");
      }
    });

    OpenUHC.getInstance().getLogger().info("Identified " + classStrings.size() + " modules.");
    OpenUHC.getInstance().getLogger().info("Successfully loaded " + moduleEntries.size() + " modules.");
  }

}
