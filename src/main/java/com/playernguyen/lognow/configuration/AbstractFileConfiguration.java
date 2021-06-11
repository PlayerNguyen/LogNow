package com.playernguyen.lognow.configuration;

import java.io.File;

import com.google.common.base.Preconditions;
import com.osiris.dyml.DYModule;
import com.osiris.dyml.DYValue;
import com.osiris.dyml.DreamYaml;
import com.osiris.dyml.exceptions.IllegalKeyException;
import com.osiris.dyml.exceptions.NotLoadedException;
import com.playernguyen.lognow.LogNow;

public abstract class AbstractFileConfiguration<T extends FileConfigurationModel> implements FileConfiguration<T> {
	private final LogNow plugin;
	private final DreamYaml yaml;

	public AbstractFileConfiguration(LogNow plugin, String fileName, Class<T> fileModel) throws Exception {
		this.plugin = plugin;

		// check and generate folder
		File dataFolder = this.plugin.getDataFolder();
		if (!dataFolder.exists() && !dataFolder.mkdirs()) {
			throw new IllegalStateException("data folder failed to access");
		}

		// check current file
		File currentFile = new File(dataFolder, fileName);
		this.yaml = new DreamYaml(currentFile.getPath());

		Preconditions.checkNotNull(fileModel);

		// check must be an enum
		if (!fileModel.isEnum()) {
			throw new IllegalStateException("fileModel must be an enum class type");
		}

		// fill up the element from model to the file
		for (T fileModelElement : fileModel.getEnumConstants()) {

			// divine keys by dots cleft
			String[] keyPath = fileModelElement.getKey().split("\\.");
			DYModule module = this.yaml.put(keyPath);

			module.addDefValues(new DYValue(fileModelElement.getValue().toString()))
					.setComments(fileModelElement.getComments());
		}

		// save before load
		yaml.saveAndLoad();

	}

	/**
	 * Return DreamYaml object, which manage this class
	 * 
	 * @return DreamYaml object
	 */
	public DreamYaml getYaml() {
		return yaml;
	}

	@Override
	public DYValue get(T key) {
		return yaml.get(key.getKey().split("\\.")).getValue();
	}

	@Override
	public DYModule set(T key, DYValue object) throws NotLoadedException, IllegalKeyException {
		return yaml.put(key.getKey().split("\\|")).setValues(object);
	}

}
