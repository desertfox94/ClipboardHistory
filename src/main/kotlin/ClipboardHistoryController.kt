import core.ClipboardModel
import core.ClipboardEntry
import tornadofx.Controller
import views.ClipboardHistoryView

class ClipboardHistoryController(val view: ClipboardHistoryView) : Controller() {

	var model = ClipboardModel()
	
	init{
		Runtime.getRuntime().addShutdownHook(Thread() { model.saveHistory() });
		view.show(model.getHistory())
		view.currentContent.textProperty().bindBidirectional(model.currentContent)
		view.currentContent.textProperty().addListener({ obs ->
			val current = model.history.current();
			if (current != null) {
				current.data = view.currentContent.text;
				model.set(current);
			}
			
			view.currentContent.editableProperty().set(current != null);
		})
	}
	
	fun clearHistory() {
		model.clear();
		view.show(model.getHistory())
	}
	
	fun select(entry : ClipboardEntry) {
		model.set(entry);
	}
	
}