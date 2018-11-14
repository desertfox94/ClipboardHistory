package core

import com.google.gson.Gson
import com.sun.glass.ui.ClipboardAssistance
import javafx.beans.property.SimpleStringProperty
import javafx.beans.value.ObservableValue
import javafx.collections.ObservableList
import javafx.scene.input.Clipboard
import sun.awt.datatransfer.ClipboardTransferable
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.io.File

class ClipboardModel {


	var historyFile = File(File("").absolutePath + File.separator + "clipboard.hst");

	var history: ClipboardHistory;
	val clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	val fxClipboard = Clipboard.getSystemClipboard();
	var availableFlavors: List<DataFlavor>;
	val clipboardAssistance: ClipboardAssistance;

	val currentContent = SimpleStringProperty()

	var currentEntry: ClipboardEntry? = null;

	constructor() {
		history = loadHistory()
		currentContent.addListener({ obs ->
			val current = history.current();
			set(ClipboardEntry(current.mimeType, current.humanPresentableName, currentContent.get()))
		})
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

	fun changed(observable: ObservableValue<String>, oldValue: String, newValue: String) {

	}

	fun handleClipboardData() {
		var data: Any;
		for (flavor in availableFlavors) {
			try {
				val transferable = clipboard.getContents(ClipboardTransferable::class)
				data = clipboard.getData(flavor);
				if (data != null) {
					history.add(ClipboardEntry(flavor, transferable.getTransferData(flavor)));
					currentContent.set(data.toString())
					break;
				}
			} catch (e: Exception) {
				// ignore
			}
		}
	}

	fun set(entry: ClipboardEntry) {
		clipboard.setContents(entry.getTransferable(), null)
	}

	fun saveHistory() {
		try {
			if (!historyFile.exists()) {
				historyFile.createNewFile()
			}
			historyFile.writeText(Gson().toJson(history))
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	fun loadHistory(): ClipboardHistory {
		if (historyFile.exists()) {
			val history = Gson().fromJson(historyFile.readText(), ClipboardHistory::class.java)
			history.makeObservable()
			return history;
		}
		return ClipboardHistory()
	}

	fun getHistory(): ObservableList<ClipboardEntry> {
		return history.entries as ObservableList<ClipboardEntry>
	}

	fun clearHistory() {
		history = ClipboardHistory();
		history.makeObservable()
	}


}