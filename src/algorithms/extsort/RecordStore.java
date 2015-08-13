/**
 * 
 */
package algorithms.extsort;

import java.io.IOException;

/**
 * Should provide buffer mechanism
 * 
 * @author yovn
 *
 */
public interface RecordStore
{
	Record readNextRecord() throws IOException;
	Record readNextRecord(long index) throws IOException;
	void destroy();

}
