import java.util.Arrays;

import com.playernguyen.lognow.settings.SettingConfigurationModel;

import org.junit.Test;

public class Configuration {

	@Test
	public void onEnumReflection() {
		Class<SettingConfigurationModel> aClass = SettingConfigurationModel.class;

		// System.out.println(Arrays.asList(aClass.getEnumConstants()));

		for (SettingConfigurationModel model : aClass.getEnumConstants()) {
			System.out.println(Arrays.asList(model.getKey().split("\\.")));
		}
	}

}
