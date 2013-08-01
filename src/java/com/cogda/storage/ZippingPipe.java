package com.cogda.storage;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Zipping pipe
 * reads from the input stream and produces zipped input stream
 * This class is not thread-safe
 */
public class ZippingPipe extends InputStream {

    private InputStream input;
    private PipedInputStream pipedInput;
    private PipedOutputStream pipedOut;
    private ZipOutputStream zipOutputStream;
    private byte[] buffer=new byte[8192];

    /**
     * Zip Pipe constructor
     * @param entryName
     * @param input
     * @throws IOException
     */
    public ZippingPipe(String entryName, InputStream input) throws IOException {
        pipedInput=new PipedInputStream();
        pipedOut=new PipedOutputStream(pipedInput);
        zipOutputStream=new ZipOutputStream(pipedOut);
        zipOutputStream.putNextEntry(new ZipEntry("data"));
    }

    /**
     * Zip Pipe constructor
     * @param input
     * @throws IOException
     */
    public ZippingPipe(InputStream input) throws IOException {
           this("data",input);
    }

    @Override
    public int read() throws IOException {
        pushFromInput();
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
    @Override
    public int read(byte[] buffer){
        pushFromInput();
        return 0;
    }
    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        pushFromInput();
        return super.read(b, off, len);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public long skip(long n) throws IOException {
        return 0;    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public int available() throws IOException {
        pushFromInput();
        return super.available();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public synchronized void mark(int readlimit) {

    }

    @Override
    public synchronized void reset() throws IOException {

    }

    @Override
    public boolean markSupported() {
        return false;
    }

    private void pushFromInput()
    {

    }
}
