import com.sun.glass.ui.ClipboardAssistance
import javafx.scene.input.Clipboard
import kotlinx.serialization.json.JSON
import sun.awt.datatransfer.ClipboardTransferable
import java.awt.Image
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.Transferable
import java.io.File
import kotlinx.serialization.Serializer

class ClipboardManager {

	var historyFile = File(File("").absolutePath + File.separator + "clipboard.hst");
	
	val history: ClipboardHistory;
	val clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	val fxClipboard = Clipboard.getSystemClipboard();
	var availableFlavors: List<DataFlavor>;
	val clipboardAssistance: ClipboardAssistance;

	constructor() {
		history = loadHistory()
		
		availableFlavors = listOf(
				DataFlavor.allHtmlFlavor,
				DataFlavor.fragmentHtmlFlavor,
				DataFlavor.imageFlavor,
				DataFlavor.javaFileListFlavor,
				DataFlavor.selectionHtmlFlavor,
				DataFlavor.stringFlavor
		)

		clipboardAssistance = object : ClipboardAssistance(com.sun.glass.ui.Clipboard.SYSTEM) {
			override fun contentChanged() {
				handleClipboardData()
			}
		}
	}

	fun handleClipboardData() {
		var data: Any;
		for (flavor in availableFlavors) {
			try {
				val transferable = clipboard.getContents(ClipboardTransferable::class)
				data = clipboard.getData(flavor);
				if (data != null) {
					history.add(ClipboardEntry(data, flavor, transferable));
					break;
				}
			} catch (e: Exception) {
				// ignore
			}
		}
	}

	fun set(entry: ClipboardEntry) {
		clipboard.setContents(entry.transferable, null);
	}
	
	fun saveHistory() {
		print("1")
		print("2")
		print("3")
		print("4")
		print("5")
		print("6")
		try {
			if (!historyFile.exists()) {
				historyFile.createNewFile()
				if (System.getProperty("os.name").startsWith("Windows")) {
					Runtime.getRuntime().exec("attrib +H " + historyFile.absolutePath);
				}
			}
		} catch (e : Exception) {
			
		}
	}

	fun loadHistory() : ClipboardHistory {
		return ClipboardHistory()
	}
	
}

class ImageTransferable(val image: Image) : Transferable {
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
