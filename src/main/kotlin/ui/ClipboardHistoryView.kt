package ui

import javafx.collections.FXCollections
import tornadofx.SmartResize
import tornadofx.View
import tornadofx.column
import tornadofx.tableview
import java.time.LocalDate
import javafx.scene.layout.GridPane
import tornadofx.*
import javafx.application.Application
import ClipboardEntry
import ClipboardHistory
import ClipboardManager

class ClipboardHistoryView() : View() {
	
    override val root = GridPane()

	val clipboardManager = ClipboardManager();
	
	init {
		title = "Clipboard History"
        with (root) {
            row {
                vbox {
                    tableview(clipboardManager.history.entries) {
                        readonlyColumn("Item", ClipboardEntry::flavor)
                        readonlyColumn("Count", ClipboardEntry::data)
                        resizeColumnsToFitContent()
                    }
                }
            }
        }
	}

}