package core
import java.util.LinkedList
import javafx.collections.FXCollections

class ClipboardHistory (var entries: MutableCollection<ClipboardEntry> = FXCollections.observableArrayList()) {

	fun add(entry: ClipboardEntry) {
		if (entries.contains(entry))
			return;
		entries.add(entry);
	}

	fun makeObservable() {
		entries = FXCollections.observableArrayList(entries)
	}
	
	fun current() : ClipboardEntry? {
		return entries.lastOrNull();
	}
	
}