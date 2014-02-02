/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package computation.with.finite.fields.computation;
import computation.with.finite.fields.interfaces.GaloisFieldArithmetic;
/**
 *
 * @author Jakub
 */
public class GaloisField implements GaloisFieldArithmetic{
    
    private static final long[] binaryPowers = {1L, 2L, 4L, 8L, 16L, 32L, 64L, 128L, 256L, 512L, 1024L, 2048L, 
        4096L, 8192L, 16384L, 32768L, 65536L, 131072L, 262144L, 524288L, 1048576L, 2097152L, 4194304L, 8388608L, 
        16777216L, 33554432L, 67108864L, 134217728L, 268435456L, 536870912L, 1073741824L, 2147483648L, 
        4294967296L, 8589934592L, 17179869184L, 34359738368L, 68719476736L, 137438953472L, 274877906944L, 
        549755813888L, 1099511627776L, 2199023255552L, 4398046511104L, 8796093022208L, 17592186044416L, 
        35184372088832L, 70368744177664L, 140737488355328L, 281474976710656L, 562949953421312L, 1125899906842624L,
        2251799813685248L, 4503599627370496L, 9007199254740992L, 18014398509481984L, 36028797018963968L, 
        72057594037927936L, 144115188075855872L, 288230376151711744L, 576460752303423488L, 1152921504606846976L,
        2305843009213693952L, 4611686018427387904L, 9223372036854775807L};
    
    
    private long reducingPolynomial;
    private short fieldSize;
    
    public GaloisField(){
        reducingPolynomial = 115;
        fieldSize = countFieldSize();
    }

    @Override
    public long add(long element1, long element2) {
        //prvky sem musia patrit, potom...
        return element1 ^ element2;
    }

    @Override
    public long subtract(long element1, long element2) {
        return add(element1, element2);
    }

    @Override
    public long multiply(long element1, long element2) {
        long result = 0;
        long carry;
        
        for(short counter = 0; counter < fieldSize; counter++){
            if( (element2 & 1) != 0 ){
                result ^= element1;
            }
            
            carry = element1 & binaryPowers[fieldSize - 1] ;
            element1 <<= 1;
            
            if(carry != 0){
                element1 ^= reducingPolynomial;
            }
            element2 >>= 1;
        }
        
        return result;
    }

    @Override
    public long divide(long element1, long element2) {
        return (multiply(element1, invert(element2)));
    }

    @Override
    public long invert(long element) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long power(long element, long exponent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public long getReducingPolynomial() {
        return reducingPolynomial;
    }

    public void setReducingPolynomial(long reducingPolynomial) {
        this.reducingPolynomial = reducingPolynomial;
    }

    public short getFieldSize(){
        return fieldSize;
    }
    
    private short countFieldSize(){
        
        short result = -1;
        long poly = reducingPolynomial;
        
        while(poly != 0){
            poly >>= 1;
            result++;
        }
        
        return result;
    }
    
}
