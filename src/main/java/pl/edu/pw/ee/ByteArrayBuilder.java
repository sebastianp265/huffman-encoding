package pl.edu.pw.ee;

import java.util.Arrays;

public class ByteArrayBuilder {

    private byte[] buffer;
    private int bufferSize;

    private int currentByteIndex = 0;
    private int currentBitPosition = 0;

    private final int[] bitMasks = {0b10000000, 0b01000000, 0b00100000, 0b00010000, 0b00001000, 0b00000100, 0b00000010, 0b00000001};

    public ByteArrayBuilder() {
        bufferSize = Byte.BYTES;
        buffer = new byte[bufferSize];
    }

    public void append(boolean bit) {
        if (currentBitPosition == 8) {
            currentBitPosition = 0;
            currentByteIndex++;
            expand();
        }

        if (bit) {
            buffer[currentByteIndex] |= bitMasks[currentBitPosition];
            //buffer[currentByteIndex] |= (bit & 0x1) << currentBitPosition;
        }
        currentBitPosition++;

    }

    public void appendByte(byte character) {
        String binaryForm = Integer.toBinaryString(Byte.toUnsignedInt(character));
        int binaryFormLength = binaryForm.length();

        for (int i = 0; i < 8 - binaryFormLength; i++) {
            append(false);
        }

        for (int i = 0; i < binaryFormLength; i++) {
            append(binaryForm.charAt(i) != 48);
        }

    }

    private void expand() {
        bufferSize += Byte.BYTES;
        buffer = Arrays.copyOf(buffer, bufferSize);
    }

    public byte[] getBuiltBytes() {
        return buffer;
    }
}
