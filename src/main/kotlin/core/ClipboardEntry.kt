import java.awt.datatransfer.DataFlavor;

class ClipboardEntry constructor(val data: Any, val flavor: DataFlavor) {

	override operator fun equals(other: Any?): Boolean {
		if (other is ClipboardEntry)
			return data.equals((other as ClipboardEntry).data);
		return false;
	}
}