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
 */
public class ZippingPipe extends InputStream {

    private InputStream input;
    private PipedInputStream pipedInput;
    private PipedOutputStream pipedOut;
    private ZipOutputStream zipOutputStream;
    private byte[] buffer=new byte[8192];
    private boolean flushed=false;

    /**
     * Zip Pipe constructor
     * @param entryName
     * @param input
     * @throws IOException
     */
    public ZippingPipe(String entryName, InputStream input) throws IOException {
        this.input=input;
        pipedInput=new PipedInputStream();
        pipedOut=new PipedOutputStream(pipedInput);
        zipOutputStream=new ZipOutputStream(pipedOut);
        zipOutputStream.putNextEntry(new ZipEntry(entryName));
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
        return pipedInput.read();
    }
    @Override
    public int read(byte[] buffer) throws IOException {
        pushFromInput();
        return pipedInput.read(buffer);
    }
    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        pushFromInput();
        return pipedInput.read(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
        return 0;
    }

    @Override
    public int available() throws IOException {
        pushFromInput();
        return pipedInput.available();
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public synchronized void mark(int readlimit) {
        pipedInput.mark(readlimit);
    }

    @Override
    public synchronized void reset() throws IOException {
           pipedInput.reset();
    }

    @Override
    public boolean markSupported() {
        return pipedInput.markSupported();
    }

    /**
     * Push data from the input stream
     * @throws IOException
     */
    private synchronized void pushFromInput() throws IOException
    {
        while(!flushed && pipedInput.available()==0)
            moveFromInput();
    }

    /**
     * Move data from the input stream to the zip pipe
     * @throws IOException
     */
    private void moveFromInput() throws IOException
    {
        if(flushed)
            return;
        int read=input.read(buffer);
        if(read==-1) {
            if(!flushed) {
                flushed=true;
                zipOutputStream.closeEntry();
                zipOutputStream.finish();
                pipedOut.flush();
                pipedOut.close();
            }
        } else
            zipOutputStream.write(buffer,0,read);

    }
}
