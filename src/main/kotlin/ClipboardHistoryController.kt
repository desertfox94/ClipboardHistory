import views.ClipboardHistoryView
import core.ClipboardModel
import tornadofx.Controller

class ClipboardHistoryController(val view: ClipboardHistoryView) : Controller() {

	var model = ClipboardModel()
	
	init{
		Runtime.getRuntime().addShutdownHook(Thread() { model.saveHistory() });
		view.show(model.getHistory())
		view.currentContent.textProperty().bindBidirectional(model.currentContent)
	}
	
	fun clearHistory() {
		model.clearHistory();
		view.show(model.getHistory())
	}
	
}