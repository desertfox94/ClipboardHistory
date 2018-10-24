import java.awt.Image
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection
import java.awt.datatransfer.Transferable
import javax.activation.DataHandler

class ClipboardManager {

	var history: ClipboardHistory = ClipboardHistory();
	val clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

	var availableFlavors: List<DataFlavor>;

	constructor() {
		availableFlavors = listOf(
				DataFlavor.allHtmlFlavor,
				DataFlavor.fragmentHtmlFlavor,
				DataFlavor.imageFlavor,
				DataFlavor.javaFileListFlavor,
				DataFlavor.selectionHtmlFlavor,
				DataFlavor.stringFlavor
		)
		clipboard.addFlavorListener { handleClipboardData() }
	}

	fun handleClipboardData() {
		var data: Any;
		for (flavor in availableFlavors) {
			try {
				data = clipboard.getData(flavor);
				if (data != null) {
					history.add(ClipboardEntry(data, flavor));
					break;
				}
			} catch (e: Exception) {
				// ignore
			}
		}
	}

	fun set(entry: ClipboardEntry) {
		if (entry.flavor == DataFlavor.imageFlavor) {
			clipboard.setContents(ImageTransferable((entry.data as Image)), null)
		} else {
			clipboard.setContents(StringSelection(entry.data as String), null);
		}
	}

}

class ImageTransferable(val image: Image): Transferable {
	override fun getTransferData(flavor: DataFlavor?): Any? {
		return image;
	}

	override fun isDataFlavorSupported(flavor: DataFlavor?): Boolean {
		return flavor == DataFlavor.imageFlavor;
	}

	override fun getTransferDataFlavors(): Array<out DataFlavor>? {
		return arrayOf(DataFlavor.imageFlavor);
	}
}
