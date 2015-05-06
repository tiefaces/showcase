package org.tiefaces.utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;

public final class FacesUtility {

	private static final int DEFAULT_STREAM_BUFFER_SIZE = 10240;
	

	public static Set<String> getResourcePaths(String path) {
		return getResourcePaths(getContext(), path);
	}	

	public static Set<String> getResourcePaths(FacesContext context, String path) {
		return context.getExternalContext().getResourcePaths(path);
	}	
	
	public static InputStream getResourceAsStream( String path) {
		return getResourceAsStream(getContext(), path);
	}

	public static InputStream getResourceAsStream(FacesContext context, String path) {
		return context.getExternalContext().getResourceAsStream(path);
	}
	
	public static Map<String, Object> getMetadataAttributes() {
		return getMetadataAttributes(getContext());
	}
	public static Map<String, Object> getMetadataAttributes(FacesContext context) {
		return context.getViewRoot().getAttributes();
	}
	
	public static <T> T evaluateExpressionGet(String expression) {
		return evaluateExpressionGet(getContext(), expression);
	}

	@SuppressWarnings("unchecked")
	public static <T> T evaluateExpressionGet(FacesContext context, String expression) {
		if (expression == null) {
			return null;
		}

		return (T) context.getApplication().evaluateExpressionGet(context, expression, Object.class);
	}
	
	public static FacesContext getContext() {
		return FacesContext.getCurrentInstance();
	}

	public static byte[] toByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		stream(input, output);
		return output.toByteArray();
	}
	public static long stream(InputStream input, OutputStream output) throws IOException {
		try (ReadableByteChannel inputChannel = Channels.newChannel(input);
			WritableByteChannel outputChannel = Channels.newChannel(output))
		{
			ByteBuffer buffer = ByteBuffer.allocateDirect(DEFAULT_STREAM_BUFFER_SIZE);
			long size = 0;

			while (inputChannel.read(buffer) != -1) {
				buffer.flip();
				size += outputChannel.write(buffer);
				buffer.clear();
			}

			return size;
		}
	}
	
	public static String stripPrefixPath(final String prefix, final String resource) {
		String normalizedResource = resource;
		if (normalizedResource.startsWith(prefix)) {
			normalizedResource = normalizedResource.substring(prefix.length() - 1);
		}

		return normalizedResource;
	}	
	
}
