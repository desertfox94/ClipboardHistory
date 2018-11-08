import javafx.application.Application
import tornadofx.App
import ui.ClipboardHistoryView

class ClipboardApp : App() {
	
   override val primaryView = ClipboardHistoryView::class

}

fun main(args: Array<String>) {
    Application.launch(ClipboardApp::class.java, *args)
}