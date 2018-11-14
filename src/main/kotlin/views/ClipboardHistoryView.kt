package views

import ClipboardHistoryController
import core.ClipboardEntry
import javafx.collections.ObservableList
import javafx.scene.control.TableView
import javafx.scene.control.TextArea
import javafx.scene.control.Tooltip
import javafx.scene.layout.AnchorPane
import tornadofx.View
import tornadofx.readonlyColumn
import java.text.SimpleDateFormat

class ClipboardHistoryView() : View() {

	override val root: AnchorPane by fxml()

	val dateFormatter = SimpleDateFormat("HH:mm:ss dd.MM.yyyy");

	val historyTableView: TableView<ClipboardEntry> by fxid()

	val currentContent: TextArea by fxid()

	val controller = ClipboardHistoryController(this);

	init {
		with(historyTableView) {
			val contentColumn = readonlyColumn("Content", ClipboardEntry::data) {
				cellFormat {
					val content = it.toString().trim()
					text = content
					tooltip = Tooltip(content)
				}
				prefWidth = 250.0
			}
			readonlyColumn("Timestamp", ClipboardEntry::date) {
				cellFormat {
					text = dateFormatter.format(it)
				}
				prefWidth = 120.0
			}
		}
	}

	fun clearHistory() {
		controller.clearHistory()
	}

	fun show(items: ObservableList<ClipboardEntry>) {
		historyTableView.setItems(items)
	}

	fun showCurrentClipboardContent(content: String?) {
		currentContent.setText(content)
		currentContent.editableProperty().set(content != null)
	}

}