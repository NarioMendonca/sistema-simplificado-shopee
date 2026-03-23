package com.shopee.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.OffsetDateTime;
import java.util.Objects;

public class Logger {
    private static Logger instance;
    private File logFile;

    private Logger() {
        logFile = new File("log.txt");
    }

    private String getExceptionStackTrace(Throwable exception) {
        // funções que irão salvar o print do stackTrace do erro
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        // converte stackTrace para string
        exception.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    private void saveLog(String log) {
        // carrega o arquivo de log como append (através do true em FileWriter), 
        // depois carrega o arquivo em um BufferedWriter (writer mais otimizado)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
            String dateNow = OffsetDateTime.now().toString();

            writer.write("timestamp=" + dateNow + " " + log);
            writer.newLine();
        } catch (IOException writeFileException) {
            writeFileException.printStackTrace();
            throw new RuntimeException("Falha ao escrever em arquivo de logs");
        }
    }

    private void writeLog(String logType, String log, Throwable exception) {
        String stackTrace = getExceptionStackTrace(exception);
        saveLog("level=" + logType + " " + "message=" + log + " " + "stackTrace=" + stackTrace);
    }

    // sobre carga do metodo writeLog para utilizar ou não o stackStace
    private void writeLog(String logType, String log) {
        saveLog(log);
    }

    //metodos de log
    public void logInfo(String log) {
        writeLog("Info", log);
    }

    public void logWarn(String log, Throwable exception) {
        writeLog("Warn", log, exception);
    }

    public void logError(String log, Throwable exception) {
        writeLog("Error", log, exception);
    }

    public void logDebug(String log, Throwable exception) {
        writeLog("Debug", log, exception);
    }

    public static Logger getInstance() {
        if (Objects.isNull(instance)) {
            instance = new Logger();
        }
        return instance;
    }
}
