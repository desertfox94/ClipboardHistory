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

class ClipboardHistoryView() : View() {

	override val root = GridPane()

	val clipboardManager = ClipboardManager();

	lateinit var table : TableView<ClipboardEntry>;
	
	init {
		title = "Clipboard History"
		with(root) {
			table = tableview(clipboardManager.history.entries) {
				setOnMouseClicked({
					if (it.button == MouseButton.SECONDARY) {
						clipboardManager.set(table.selectionModel.selectedItemProperty().get())
					}
				})
				readonlyColumn("Item", ClipboardEntry::flavor)
				readonlyColumn("Count", ClipboardEntry::data).contentWidth(useAsMax = true)
//                        resizeColumnsToFitContent()
			}

		}
	}

}