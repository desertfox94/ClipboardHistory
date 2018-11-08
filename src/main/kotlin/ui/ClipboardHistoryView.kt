package ui

import ClipboardEntry
import ClipboardManager
import javafx.scene.control.TableRow
import javafx.scene.control.TableView
import javafx.scene.layout.GridPane
import javafx.util.Callback
import tornadofx.View
import tornadofx.contentWidth
import tornadofx.readonlyColumn
import tornadofx.tableview
import javafx.scene.input.MouseButton
import java.text.SimpleDateFormat
import javafx.scene.control.Tooltip

class ClipboardHistoryView() : View() {

	override val root = GridPane()

	val clipboardManager = ClipboardManager();

	val dateFormatter = SimpleDateFormat("HH:mm:ss dd.MM.yyyy");

	lateinit var table: TableView<ClipboardEntry>;

	init {
		title = "Clipboard History"
		with(root) {
			table = tableview(clipboardManager.history.entries) {
				setOnMouseClicked({
					if (it.button == MouseButton.SECONDARY) {
						clipboardManager.set(table.selectionModel.selectedItemProperty().get())
					}
				})
				readonlyColumn("Type", ClipboardEntry::flavor).cellFormat {
					text = it?.subType
				}
				readonlyColumn("Content", ClipboardEntry::data).cellFormat {
					val content = it.toString();
					tooltip = Tooltip(it.toString())
					text = content
				}
				readonlyColumn("Timestamp", ClipboardEntry::date).cellFormat {
					text = dateFormatter.format(it);
				}
			}
		}
		
		Runtime.getRuntime().addShutdownHook(Thread() { clipboardManager.saveHistory() });
	}

}