package com.gryoscopepong;

import android.content.Context;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.gryoscopepong.GryoscopePong;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Calendar;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new GryoscopePong(), config);

		//read file
		try {
			FileInputStream input = getApplicationContext().openFileInput("save_data.txt");
			InputStreamReader inputStreamReader = new InputStreamReader(input, Charset.forName("UTF-8"));//byte by byte
			BufferedReader reader = new BufferedReader(inputStreamReader);//just for ease of use
			String line = reader.readLine();

			while(line != null){
				System.out.println(line);
				line = reader.readLine();
			}

			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		//save file
		try{
			FileOutputStream outputStream = getApplicationContext().openFileOutput("save_data.txt", Context.MODE_APPEND);
			outputStream.write("test data to write\n".getBytes(Charset.forName("UTF-8")));
			System.out.println("wrote data");
			outputStream.close();
		}catch (Exception e){
			e.printStackTrace();
		}



	}
}
