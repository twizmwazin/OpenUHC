package in.twizmwaz.openuhc.module;

import com.google.common.collect.ImmutableMap;
import in.twizmwaz.openuhc.OpenUHC;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import lombok.NonNull;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;


public class ModuleFactory {

  private static final String MODULE_DESCRIPTOR = Type.getDescriptor(Module.class);

  final Set<ModuleData> moduleData = new HashSet<>();

  /**
   * Finds all modules in a loaded jar and builds them.
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
    // Now that we have located ModuleEntries, for each load appropriate data
    for (String classString : classStrings) {
      try {
        // Get the class from the name ASM found
        final Class clazz = Class.forName(classString);
        Annotation annotation = clazz.getAnnotation(Module.class);
        // Verify module annotation exists
        if (annotation == null) {
          OpenUHC.getInstance().getLogger().warning(clazz + " is an invalid module, skipping.");
          continue;
        }
        final Module module = (Module) annotation;

        // Find settings
        final Map<String, Class> settings = new HashMap<>();
        for (Field field : clazz.getFields()) {
          annotation = field.getAnnotation(Setting.class);
          if (annotation != null) {
            settings.put(((Setting) annotation).value(), field.getType());
          }
        }

        annotation = clazz.getAnnotation(Scenario.class);
        // Check if it is a scenario
        if (annotation == null) {
          // It is not
          final ModuleData data
              = new ModuleData(clazz, module.lifeCycle(), module.enableOnStart(), ImmutableMap.copyOf(settings));
          moduleData.add(data);
        } else {
          // It is
          final Scenario scenario = (Scenario) annotation;
          final ScenarioData data = new ScenarioData(clazz, module.lifeCycle(), module.enableOnStart(),
              ImmutableMap.copyOf(settings), scenario.name(), scenario.desc());
        }


      } catch (ClassNotFoundException ex) {
        OpenUHC.getInstance().getLogger().warning("ASM found module '" + classString
            + "' but it could not be located, skipping.");
      }
    }

    OpenUHC.getInstance().getLogger().info("Identified " + classStrings.size() + " modules.");
    OpenUHC.getInstance().getLogger().info("Successfully loaded " + moduleData.size() + " modules.");
  }

}
