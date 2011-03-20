package filter;

import java.io.ByteArrayOutputStream;

public class ByteArrayOutputStream2 extends ByteArrayOutputStream {
	public ByteArrayOutputStream2() {
		super();
	}

	public ByteArrayOutputStream2(int size) {
		super(size);
	}

	public byte[] getBuf() {
		return buf;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void reset(int minSize) {
		reset();
		if (buf.length < minSize) {
			buf = new byte[minSize];
		}
	}

	public void writeUnchecked(int b) {
		buf[count++] = (byte) b;
	}

}
