import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable
import java.util.Date
import kotlinx.serialization.Serializable

@Serializable
data class ClipboardEntry constructor(val data: Any?, val flavor: DataFlavor?, val transferable : Transferable?, val date : Date? = Date()) {

	constructor () : this(null,null,null,null) {
	}
	
	override operator fun equals(other: Any?): Boolean {
		if (other is ClipboardEntry)
			return data != null && data.equals((other as ClipboardEntry).data);
		return false;
	}
}