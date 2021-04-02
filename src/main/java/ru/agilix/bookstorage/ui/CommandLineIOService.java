package ru.agilix.bookstorage.ui;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class CommandLineIOService implements IOService {

    @Override
    public String getString() {
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        try {
            return buffer.readLine();
        } catch (IOException e) {
            putString("Error: "+e.toString());
            return "";
        }
    }

    @Override
    public void putString(String text) {
        System.out.println(text);
    }
}
