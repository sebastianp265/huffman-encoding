package pl.edu.pw.ee;

public class BitReader {

    private byte[] bytes;

    private int currentByteIndex = 0;
    private int currentBitPosition = 0;

    private final int[] bitMasks = {0b10000000, 0b01000000, 0b00100000,
        0b00010000, 0b00001000, 0b00000100, 0b00000010, 0b00000001};

    private int lastByteIndex;
//    private int lastBitPosition;

    public BitReader(byte[] bytes) {
        this.bytes = bytes;
        lastByteIndex = bytes.length - 1;
//        calculateLastBit();
    }

    public boolean hasNextBit() {
        return currentBitPosition <= 7
                && currentByteIndex <= lastByteIndex;
    }

//    public boolean hasNextByte() {
//        return currentBitPosition != 0
//                && currentByteIndex < bytes.length - 1;
//    }
//
//    private void calculateLastBit() {
//        int i = bytes.length - 1;
//        int j = 7;
//        outer:
//        for (; i >= 0; i--) {
//            j = 7;
//            for (; j >= 0; j--) {
//                if ((bitMasks[j] & bytes[i]) == 0) {
//                    break outer;
//                }
//            }
//        }
//
//        lastByteIndex = j;
//        lastBitPosition = i;
//    }

    public int nextBit() {
        if (getFilteredByte() != 0) {
            return 1;
        } else {
            return 0;
        }
    }

    private int getFilteredByte() {
        int filteredByte = bytes[currentByteIndex] & bitMasks[currentBitPosition];
        currentBitPosition++;
        if (currentBitPosition == 8) {
            currentByteIndex++;
            currentBitPosition = 0;
        }
        return filteredByte;
    }

    public int nextByte() {
        int result = 0;
        for (int i = 0; i < 8 && hasNextBit(); i++) {
            result |= getFilteredByte();
        }
        return result;
    }

}
