/*
 * Copyright (c) 2012, Agivis Sistemas LTDA. Todos os direitos reservados.
 *
 * Os Programas desta Aplicação (que incluem tanto o software quanto a sua
 * documentação) contém informações proprietárias da Agivis Sistemas LTDA; eles são
 * licenciados de acordo com um contrato de licença contendo restrições de uso e
 * confidencialidade, e são também protegidos pela Lei 9609/98 e 9/610/98,
 * respectivamente Lei do Software e Lei dos Direitos Autorais. Engenharia
 * reversa, descompilação e desmontagem dos programas são proibidos. Nenhuma
 * parte destes programas pode ser reproduzida ou transmitida de nenhuma forma e
 * por nenhum meio, eletrônico ou mecânico, por motivo algum, sem a permissão
 * escrita da Agivis Sistemas LTDA.
 */
package br.com.agivis.framework.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

//import de.akquinet.android.androlog.Log;

/**
 * Métodos para gerenciar arquivos.
 * 
 * @author Time Agivis
 *
 */
public class FileUtils {
	
	/**
	 * Realiza download e escreve o conteúdo no stream de saída.
	 * @param fileUrl
	 * @param localFile
	 * @throws IOException
	 */
	public void download(String fileUrl, String localFile) throws IOException {
		download(fileUrl, localFile, null);
	}

	/**
	 * Realiza download e escreve o conteúdo no stream de saída monitorando o progresso.
	 * @param fileUrl
	 * @param localFile
	 * @throws IOException
	 */
	public void download(String fileUrl, String localFile, TransferProgressHandler handler) throws IOException {
		URL url = new URL(fileUrl);
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		
		int size = urlConnection.getContentLength();
		
		//Log.d("tamanho do arquivo: " + size);
		
		InputStream in = null;
		OutputStream out = null;
		
		try {
			in = new BufferedInputStream(urlConnection.getInputStream(), 4096);
			out = new BufferedOutputStream(new FileOutputStream(localFile), 4096);
			
			byte[] buffer = new byte[4096];
			int count;
			int downloaded = 0;
			while ((count = in.read(buffer,0, 4096)) != -1) {
				out.write(buffer, 0, count);
				downloaded += count;
				
				if (handler != null) {
					handler.transfered(downloaded, size);
				}
			}
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
			
			urlConnection.disconnect();
			
			if (in != null) {
				in.close();
			}
		}
	}

	public void unzip(String pathZippedFile, String pathDestination) throws FileNotFoundException, IOException {
		InputStream input = new FileInputStream(pathZippedFile);
		ZipInputStream zis = new ZipInputStream(new BufferedInputStream(input, 4096));
		 try {
		     ZipEntry ze;
		     
		     while ((ze = zis.getNextEntry()) != null) {
		    	 if (ze.isDirectory()) {
		    		 File directory = new File(ze.getName());
		    		 directory.mkdir();
		    		 continue;
		    	 }
		    	 
		    	 String filePath = pathDestination + File.separator + ze.getName();
		    	 int indexLastSeparator = filePath.lastIndexOf(File.separatorChar);
		    	 
		    	 if (indexLastSeparator > 0) {
		    		 File directory = new File(filePath.substring(0, indexLastSeparator));
		    		 directory.mkdirs();
		    	 }
		    	 
		    	 OutputStream out = new BufferedOutputStream(new FileOutputStream(filePath), 4096);
		    	 
		    	 try {
			         byte[] buffer = new byte[4096];
			         int count;
			         while ((count = zis.read(buffer, 0, 4096)) != -1) {
			             out.write(buffer, 0, count);
			         }
		    	 } finally {
		    		 out.flush();
		    		 out.close();
		    	 }
		     }
		 } finally {
		     zis.close();
		 }
	}
	
	public InputStream openFileInput(String path) throws FileNotFoundException {
		return new BufferedInputStream(new FileInputStream(path), 16384);
	}

	public void deleteDir(String path) {
		deleteDir(new File(path));
	}
	
	public boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
	        String[] children = dir.list();
	        for (int i=0; i<children.length; i++) {
	            boolean success = deleteDir(new File(dir, children[i]));
	            if (!success) {
	                return false;
	            }
	        }
	    }

	    return dir.delete();
	}

}
