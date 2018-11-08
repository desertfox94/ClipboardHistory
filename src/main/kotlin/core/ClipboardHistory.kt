import javafx.collections.ObservableList
import javafx.collections.FXCollections
import kotlinx.serialization.Serializable

@Serializable
data class ClipboardHistory (val entries: ObservableList<ClipboardEntry>) {

	constructor () : this(FXCollections.observableArrayList())
	
	fun add(entry: ClipboardEntry) {
		if (entries.contains(entry))
			return;
		entries.add(entry);
	}

}