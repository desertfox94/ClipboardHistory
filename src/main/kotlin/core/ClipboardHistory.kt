import javafx.collections.ObservableList
import javafx.collections.FXCollections

class ClipboardHistory {

	val entries: ObservableList<ClipboardEntry> = FXCollections.observableArrayList();

	fun add(entry: ClipboardEntry) {
		if (entries.contains(entry))
			return;
		entries.add(entry);
	}

}