/**
 * 
 */
package algorithms.extsort;

import java.io.IOException;
import java.math.BigInteger;

/**
 * Should provide buffer mechanism
 * 
 * @author yovn
 *
 */
public interface RecordStore
{
	Record readNextRecord() throws IOException;
	Record readNextRecord(BigInteger index) throws IOException;
	void destroy();

}
