package org.tiefaces.showcase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Source Utility.
 * @author Jason Jiang
 *
 */
public class SourceUtility {
	
	/**
	 * hide constructor.
	 */
	private SourceUtility() {
		// not called
	}
	

	/**
	 * default stream buffer setting.
	 */
	private static final int DEFAULT_STREAM_BUFFER_SIZE = 10240;
	
	
	/**
	 * convert input stream to byte array.
	 * 
	 * @param input
	 *            input stream.
	 * @return byte array.
	 * @throws IOException
	 *             throw exception.
	 */
	public static byte[] toByteArray(final InputStream input)
			throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		stream(input, output);
		return output.toByteArray();
	}

	/**
	 * stream from input into output.
	 * 
	 * @param input
	 *            input stream.
	 * @param output
	 *            output stream.
	 * @return stream size.
	 * @throws IOException
	 *             Exception.
	 */

	public static long stream(final InputStream input,
			final OutputStream output) throws IOException {
		try (ReadableByteChannel inputChannel = Channels.newChannel(input);
				WritableByteChannel outputChannel = Channels
						.newChannel(output)) {
			ByteBuffer buffer = ByteBuffer
					.allocateDirect(DEFAULT_STREAM_BUFFER_SIZE);
			long size = 0;

			while (inputChannel.read(buffer) != -1) {
				buffer.flip();
				size += outputChannel.write(buffer);
				buffer.clear();
			}

			return size;
		}
	}
	

}
