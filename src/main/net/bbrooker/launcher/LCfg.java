package net.bbrooker.launcher;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

import net.bbrooker.launcher.Util;

public class Config {
	
	public static Config instance = new Config();
	private Properties prop = new Properties();
	
	public final URL AUTHENTICATION_URL, FILELIST_URL, CHECKSUM_URL, DOWNLOAD_DIRECTORY_URL, LAUNCHER_DOWNLOAD_URL,
							LAUNCHER_VERSION_URL, NEWS_DATA_URL;
	public final String MCSERVER_HOST;
	public final int MCSERVER_PORT;
	public final File MC_BASEPATH;
	
	public Config() {
		try {
			prop.load(getClass().getResourceAsStream("/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		AUTHENTICATION_URL = Util.safeURL(get("authentication_url"));
		FILELIST_URL = Util.safeURL(get("filelist_url"));
		CHECKSUM_URL = Util.safeURL(get("checksum_url"));
		DOWNLOAD_DIRECTORY_URL = Util.safeURL(get("download_directory_url"));
		LAUNCHER_DOWNLOAD_URL = Util.safeURL(get("launcher_download_url"));
		LAUNCHER_VERSION_URL = Util.safeURL(get("launcher_version_url"));
		NEWS_DATA_URL = Util.safeURL(get("news_data_url"));
		MCSERVER_HOST = get("mcserver_host");
		MCSERVER_PORT = Integer.parseInt(get("mcserver_port"));
		MC_BASEPATH = new File(Util.getAppdata(), get("mc_basepath"));
	}
	
	public String get(String key) {
		return prop.getProperty(key);
	}
	
	public String getNewsContent() {
		try {
			return new Scanner(NEWS_DATA_URL.openStream(), "UTF-8").useDelimiter("\\A").next();
		} catch (IOException e) {
			e.printStackTrace();
			return "ERROR: " + e.getMessage();
		}
	}
	
	public MinecraftServerStatus getServerStatus() throws IOException {
		return new MinecraftServerStatus(MCSERVER_HOST, MCSERVER_PORT);
	}
}
