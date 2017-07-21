package in.twizmwaz.openuhc.module;

import com.google.common.collect.ImmutableMap;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
public class ScenarioData extends ModuleData {

  private final String name;
  private final String description;

  /**
   * Creates data for a scenario, which encompasses both {@link Scenario} and {@link Module}.
   *
   * @param clazz Type class
   * @param lifeCycle Module life cycle
   * @param enabledOnStart Enable the module on start
   * @param settings The settings map for the module
   * @param name The name of the scenario
   * @param description The human-readable description of the scenario
   */
  public ScenarioData(Class<? extends IModule> clazz, LifeCycle lifeCycle, boolean enabledOnStart,
                      ImmutableMap<String, Class> settings, String name, String description) {
    super(clazz, lifeCycle, enabledOnStart, settings);
    this.name = name;
    this.description = description;
  }

}
