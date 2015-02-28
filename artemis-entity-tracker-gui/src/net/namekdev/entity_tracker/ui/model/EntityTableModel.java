package net.namekdev.entity_tracker.ui.model;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class EntityTableModel extends DefaultTableModel {
	private Map<Integer, BitSet> _entitiesComponents = new HashMap<Integer, BitSet>();

	public EntityTableModel() {
		super(new Object[][] {}, new String[] { "  entity id  " });
	}

	public void setComponentType(int index, String name) {
		for (int i = getColumnCount(); i <= index+1; ++i) {
			addColumn("");
		}

		columnIdentifiers.set(index+1, name);
		fireTableStructureChanged();
	}

	public void addEntity(int entityId, BitSet components) {
		// TODO check if bitset isn't greater than before model header columns

		Vector<Object> row = new Vector<Object>(components.length() + 1);
		row.add(entityId);

		for (int i = 0, n = components.size(); i < n; ++i) {
			row.add(components.get(i));
		}

		this.addRow(row);
		_entitiesComponents.put(entityId, components);
	}

	public void removeEntity(int entityId) {
		for (int i = 0, n = getRowCount(); i < n; ++i) {
			Integer val = (Integer) getValueAt(i, 0);

			if (val == entityId) {
				removeRow(i);
				break;
			}
		}
		_entitiesComponents.remove(entityId);
	}

	public BitSet getEntityComponents(int entityId) {
		return _entitiesComponents.get(entityId);
	}

	public String getComponentName(int index) {
		return (String) columnIdentifiers.get(index + 1);
	}

	public Class<?> getColumnClass(int columnIndex) {
		return columnIndex == 0 ? Integer.class : Boolean.class;
	}

	public boolean isCellEditable(int row, int column) {
		return false;
	}
}