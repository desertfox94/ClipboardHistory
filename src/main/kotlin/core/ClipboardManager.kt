import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.FlavorEvent;
import java.awt.datatransfer.StringSelection
import java.awt.datatransfer.DataFlavor

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
			} catch(e: Exception) {
				// ignore
			}
		}
	}

}