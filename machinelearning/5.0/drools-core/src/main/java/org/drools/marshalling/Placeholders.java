package org.drools.marshalling;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

import org.drools.util.AbstractHashTable;
import org.drools.util.Entry;

public class Placeholders extends AbstractHashTable {
    private List<Object> ids;

    public Placeholders() {
        super();
        this.ids = new ArrayList<Object>();
    }
    
    public PlaceholderEntry lookupPlaceholder(Object object, PersisterKey key) {
        final int hashCode = key.hashCode( object );
        final int index = indexOf( hashCode,
                                   this.table.length );
        
        PlaceholderEntry current = (PlaceholderEntry) this.table[index];
        while ( current != null ) {
            if ( hashCode == current.hashCode && current.key == key && key.equal( object,
                                                                                  current.object ) ) {
                return current;
            }
            current = (PlaceholderEntry) current.getNext();
        }
        return null;
    }

    public PlaceholderEntry assignPlaceholder(Object object,
                                              PersisterKey key) {
        final int hashCode = key.hashCode( object );
        final int index = indexOf( hashCode,
                                   this.table.length );

        int id = ids.size();
        this.ids.add( object );
        PlaceholderEntry entry = new PlaceholderEntry( object,
                                                       id,
                                                       key );

        entry.next = this.table[index];
        this.table[index] = entry;

        if ( this.size++ >= this.threshold ) {
            resize( 2 * this.table.length );
        }

        return entry;
    }

    public Object lookupObject(int id) {
        return this.ids.get( id );
    }

    public static class PlaceholderEntry
        implements
        Entry {

        private static final long serialVersionUID = 400L;

        public Object             object;

        public int                id;

        public PersisterKey       key;

        public int                hashCode;

        public Entry              next;

        //        private LinkedList              list;

        public PlaceholderEntry(final Object object,
                                final int id,
                                final PersisterKey key) {
            this.object = object;
            this.id = id;
            this.key = key;
            this.hashCode = key.hashCode( object );
        }

        //    public PlaceholderEntry(final InternalFactHandle handle,
        //                            final int hashCode) {
        //        this.handle = handle;
        //        this.hashCode = hashCode;
        //        //            this.list = new LinkedList();
        //    }

        public Entry getNext() {
            return this.next;
        }

        public void setNext(final Entry next) {
            this.next = next;
        }

        public int hashCode() {
            return this.hashCode;
        }

        public String toString() {
            return "Placeholder( object=" + this.object + " id = " + this.id + " hashcode=" + this.hashCode + " next=" + this.next + " )";
        }

        public Object getObject() {
            return object;
        }

        public int getId() {
            return id;
        }

        public PersisterKey getKey() {
            return key;
        }

    }

    @Override
    public Entry getBucket(Object object) {
        // TODO Auto-generated method stub
        return null;
    }

}
