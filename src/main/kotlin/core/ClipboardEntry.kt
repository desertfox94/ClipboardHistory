import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable
import java.util.Date

class ClipboardEntry constructor(val data: Any, val flavor: DataFlavor, val transferable : Transferable, val date : Date = Date()) {

	override operator fun equals(other: Any?): Boolean {
		if (other is ClipboardEntry)
			return data.equals((other as ClipboardEntry).data);
		return false;
	}
}