package IO;


/**
 * Cache' Java Class Generated for class IO.InternalIO on version Cache for Windows (x86-32) 2010.1.4 (Build 803) Tue Aug 24 2010 00:04:40 EDT.<br>
 *
 * @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO</A>
**/

public class InternalIO extends com.intersys.classes.Persistent {
    private static final long serialVersionUID = 3413;
    private static String CACHE_CLASS_NAME = "IO.InternalIO";
    /**
           <p>NB: DO NOT USE IN APPLICATION(!!!).
           <br>Use <code>IO.InternalIO.open</code> instead!</br></p>
           <p>
           Used to construct a Java object, corresponding to existing object
           in Cache database.
           </p>
           @see #_open(com.intersys.objects.Database, com.intersys.objects.Oid)
           @see #open(com.intersys.objects.Database, com.intersys.objects.Oid)
    */
    public InternalIO (com.intersys.cache.CacheObject ref) throws com.intersys.objects.CacheException {
        super (ref);
    }
    public InternalIO (com.intersys.objects.Database db, String initstr) throws com.intersys.objects.CacheException {
        super (((com.intersys.cache.SysDatabase)db).newCacheObject (CACHE_CLASS_NAME,initstr));
    }
    /**
       Creates a new instance of object "IO.InternalIO" in Cache
       database and corresponding object of class
       <code>IO.InternalIO</code>.

       @param db <code>Database</code> object used for connection with
       Cache database.

       @throws com.intersys.objects.CacheException in case of error.

              @see #_open(com.intersys.objects.Database, com.intersys.objects.Oid)
              @see #open(com.intersys.objects.Database, com.intersys.objects.Oid)
     */
    public InternalIO (com.intersys.objects.Database db) throws com.intersys.objects.CacheException {
        super (((com.intersys.cache.SysDatabase)db).newCacheObject (CACHE_CLASS_NAME));
    }
    /**
    * Calls method
    * {@link com.intersys.objects.Database#createObjects(String, java.util.Collection)}
    * to save a collection of previously created new objects in Cache
    * Database. No object in collection could been saved before. All
    * objects must be instances of <code>IO.InternalIO</code> and not of its
    * subclasses. To save changes in objects that already exist in
    * the database use <code>saveObjects</code> method or instance
    * method <code>save</code> on the object itself.
    *
    * @param db <code>Database</code> object used for connection with
    * Cache database.
    *
    * @param objects A collection of newly created objects. None of
    * the objects in the collection may already exist in database.
    *
    * @see #saveObjects(com.intersys.objects.Database, java.util.Collection)
    * @see com.intersys.objects.Database#createObjects(String, java.util.Collection)
    *
    */
    public static void createObjects (com.intersys.objects.Database db, java.util.Collection objects) throws com.intersys.objects.CacheException {
        db.createObjects (CACHE_CLASS_NAME, objects);
    }
    /**
       Runs method <code> %OpenId </code> in Cache to open an object
       from Cache database and creates corresponding object of class
       <code>IO.InternalIO</code>.

       @return <code> RegisteredObject </code>, corresponding to opened
       object. This object may be of <code>IO.InternalIO</code> or of
      any of its subclasses. Cast to <code>IO.InternalIO</code> is
      guaranteed to pass without <code>ClassCastException</code> exception.

       @param db <code>Database</code> object used for connection with
       Cache database.

       @param id ID as specified in Cache represented as
      <code>Id</code>.

       @throws com.intersys.objects.CacheException in case of error.
      @see java.lang.ClassCastException
           @see #_open(com.intersys.objects.Database, com.intersys.objects.Oid)
           @see #open(com.intersys.objects.Database, com.intersys.objects.Oid)
           @see #InternalIO
     */
    public static com.intersys.classes.RegisteredObject _open (com.intersys.objects.Database db, com.intersys.objects.Id id) throws com.intersys.objects.CacheException {
        return open(db, id);
    }
    /**
       Runs method <code> %OpenId </code> in Cache to open an object
       from Cache database and creates corresponding object of class
       <code>IO.InternalIO</code>.

       @return <code> RegisteredObject </code>, corresponding to opened
       object. This object may be of <code>IO.InternalIO</code> or of
      any of its subclasses. Cast to <code>IO.InternalIO</code> is
      guaranteed to pass without <code>ClassCastException</code> exception.

       @param db <code>Database</code> object used for connection with
       Cache database.

       @param id ID as specified in Cache represented as
      <code>Id</code>.

       @throws com.intersys.objects.CacheException in case of error.
      @see java.lang.ClassCastException
           @see #_open(com.intersys.objects.Database, com.intersys.objects.Oid)
           @see #open(com.intersys.objects.Database, com.intersys.objects.Oid)
           @see #InternalIO
     */
    public static com.intersys.classes.RegisteredObject open (com.intersys.objects.Database db, com.intersys.objects.Id id) throws com.intersys.objects.CacheException {
        com.intersys.cache.CacheObject cobj = (((com.intersys.cache.SysDatabase)db).openCacheObject(CACHE_CLASS_NAME, id.toString()));
        return (com.intersys.classes.RegisteredObject)(cobj.newJavaInstance());
    }
    /**
       Runs method <code> %OpenId </code> in Cache to open an object
       from Cache database and creates corresponding object of class
       <code>IO.InternalIO</code>.

       @return <code> RegisteredObject </code>, corresponding to opened
       object. This object may be of <code>IO.InternalIO</code> or of
      any of its subclasses. Cast to <code>IO.InternalIO</code> is
      guaranteed to pass without <code>ClassCastException</code> exception.

       @param db <code>Database</code> object used for connection with
       Cache database.

       @param id ID as specified in Cache represented as
      <code>Id</code>.
      @param concurrency Concurrency level.  represented as
      <code>Concurrency</code>.

      Here are concurrency values, see Object Concurrency Options in your on-line Cache' documentation for more information.
      @see <a href = "http://cache-server:57772/csp/documatic/DocBook.UI.Page.cls?KEY=GOBJ_concurrency"> Object Concurrency Options.</A>

      <TABLE border="1"
      summary="Object Concurrency Options.">
      <CAPTION><EM>Object Concurrency Options</EM></CAPTION>
      <TR><TD>-1 </TD><TD>Default concurrency</TD></TR>
      <TR><TD>0 </TD><TD>No locking, no locks are used</TD></TR>
      <TR><TD>1 </TD><TD>Atomic</TD></TR>
      <TR><TD>2 </TD><TD>Shared</TD></TR>
      <TR><TD>3 </TD><TD>Shared/Retained</TD></TR>
      <TR><TD>4 </TD><TD>Exclusive</TD></TR>
      </TABLE>

       @throws com.intersys.objects.CacheException in case of error.
      @see java.lang.ClassCastException
           @see #_open(com.intersys.objects.Database, com.intersys.objects.Oid)
           @see #open(com.intersys.objects.Database, com.intersys.objects.Oid)
           @see #InternalIO
     */
    public static com.intersys.classes.RegisteredObject _open (com.intersys.objects.Database db, com.intersys.objects.Id id, int concurrency) throws com.intersys.objects.CacheException {
        return open(db, id, concurrency);
    }
    /**
       Runs method <code> %OpenId </code> in Cache to open an object
       from Cache database and creates corresponding object of class
       <code>IO.InternalIO</code>.

       @return <code> RegisteredObject </code>, corresponding to opened
       object. This object may be of <code>IO.InternalIO</code> or of
      any of its subclasses. Cast to <code>IO.InternalIO</code> is
      guaranteed to pass without <code>ClassCastException</code> exception.

       @param db <code>Database</code> object used for connection with
       Cache database.

       @param id ID as specified in Cache represented as
      <code>Id</code>.
      @param concurrency Concurrency level.  represented as
      <code>Concurrency</code>.

      Here are concurrency values, see Object Concurrency Options in your on-line Cache' documentation for more information.
      @see <a href = "http://cache-server:57772/csp/documatic/DocBook.UI.Page.cls?KEY=GOBJ_concurrency"> Object Concurrency Options.</A>

      <TABLE border="1"
      summary="Object Concurrency Options.">
      <CAPTION><EM>Object Concurrency Options</EM></CAPTION>
      <TR><TD>-1 </TD><TD>Default concurrency</TD></TR>
      <TR><TD>0 </TD><TD>No locking, no locks are used</TD></TR>
      <TR><TD>1 </TD><TD>Atomic</TD></TR>
      <TR><TD>2 </TD><TD>Shared</TD></TR>
      <TR><TD>3 </TD><TD>Shared/Retained</TD></TR>
      <TR><TD>4 </TD><TD>Exclusive</TD></TR>
      </TABLE>

       @throws com.intersys.objects.CacheException in case of error.
      @see java.lang.ClassCastException
           @see #_open(com.intersys.objects.Database, com.intersys.objects.Oid)
           @see #open(com.intersys.objects.Database, com.intersys.objects.Oid)
           @see #InternalIO
     */
    public static com.intersys.classes.RegisteredObject open (com.intersys.objects.Database db, com.intersys.objects.Id id, int concurrency) throws com.intersys.objects.CacheException {
        com.intersys.cache.CacheObject cobj = (((com.intersys.cache.SysDatabase)db).openCacheObject(CACHE_CLASS_NAME, id.toString(), concurrency));
        return (com.intersys.classes.RegisteredObject)(cobj.newJavaInstance());
    }
    /**
       Runs method <code> %Open </code> in Cache to open an object
       from Cache database and creates corresponding object of class
       <code>IO.InternalIO</code>.

       @return <code> RegisteredObject </code>, corresponding to opened
       object. This object may be of <code>IO.InternalIO</code> or of
      any of its subclasses. Cast to <code>IO.InternalIO</code> is
      guaranteed to pass without <code>ClassCastException</code> exception.

       @param db <code>Database</code> object used for connection with
       Cache database.
       @param oid Object ID as specified in Cache. represented as
      <code>Oid</code>.


       @throws com.intersys.objects.CacheException in case of error.
      @see java.lang.ClassCastException
           @see #_open(com.intersys.objects.Database, com.intersys.objects.Oid)
           @see #open(com.intersys.objects.Database, com.intersys.objects.Oid)
           @see #InternalIO(com.intersys.objects.Database)
     */
    public static com.intersys.classes.RegisteredObject _open (com.intersys.objects.Database db, com.intersys.objects.Oid oid) throws com.intersys.objects.CacheException {
        return open(db, oid);
    }
    /**
       Runs method <code> %Open </code> in Cache to open an object
       from Cache database and creates corresponding object of class
       <code>IO.InternalIO</code>.

       @return <code> RegisteredObject </code>, corresponding to opened
       object. This object may be of <code>IO.InternalIO</code> or of
      any of its subclasses. Cast to <code>IO.InternalIO</code> is
      guaranteed to pass without <code>ClassCastException</code> exception.

       @param db <code>Database</code> object used for connection with
       Cache database.
       @param oid Object ID as specified in Cache. represented as
      <code>Oid</code>.


       @throws com.intersys.objects.CacheException in case of error.
      @see java.lang.ClassCastException
           @see #_open(com.intersys.objects.Database, com.intersys.objects.Oid)
           @see #open(com.intersys.objects.Database, com.intersys.objects.Oid)
           @see #InternalIO(com.intersys.objects.Database)
     */
    public static com.intersys.classes.RegisteredObject open (com.intersys.objects.Database db, com.intersys.objects.Oid oid) throws com.intersys.objects.CacheException {
        com.intersys.cache.CacheObject cobj = (((com.intersys.cache.SysDatabase)db).openCacheObject(CACHE_CLASS_NAME, oid.getData()));
        return (com.intersys.classes.RegisteredObject)(cobj.newJavaInstance());
    }
    /**
       Runs method <code> %Open </code> in Cache to open an object
       from Cache database and creates corresponding object of class
       <code>IO.InternalIO</code>.

       @return <code> RegisteredObject </code>, corresponding to opened
       object. This object may be of <code>IO.InternalIO</code> or of
      any of its subclasses. Cast to <code>IO.InternalIO</code> is
      guaranteed to pass without <code>ClassCastException</code> exception.

       @param db <code>Database</code> object used for connection with
       Cache database.
       @param oid Object ID as specified in Cache. represented as
      <code>Oid</code>.
      @param concurrency Concurrency level.  represented as
      <code>Concurrency</code>.

      Here are concurrency values, see Object Concurrency Options in your on-line Cache' documentation for more information.
      @see <a href = "http://cache-server:57772/csp/documatic/DocBook.UI.Page.cls?KEY=GOBJ_concurrency"> Object Concurrency Options.</A>

      <TABLE border="1"
      summary="Object Concurrency Options.">
      <CAPTION><EM>Object Concurrency Options</EM></CAPTION>
      <TR><TD>-1 </TD><TD>Default concurrency</TD></TR>
      <TR><TD>0 </TD><TD>No locking, no locks are used</TD></TR>
      <TR><TD>1 </TD><TD>Atomic</TD></TR>
      <TR><TD>2 </TD><TD>Shared</TD></TR>
      <TR><TD>3 </TD><TD>Shared/Retained</TD></TR>
      <TR><TD>4 </TD><TD>Exclusive</TD></TR>
      </TABLE>

      @throws com.intersys.objects.CacheException in case of error.
      @see java.lang.ClassCastException
           @see #_open(com.intersys.objects.Database, com.intersys.objects.Oid)
           @see #open(com.intersys.objects.Database, com.intersys.objects.Oid)
           @see #InternalIO(com.intersys.objects.Database)
     */
    public static com.intersys.classes.RegisteredObject _open (com.intersys.objects.Database db, com.intersys.objects.Oid oid, int concurrency) throws com.intersys.objects.CacheException {
        return open(db, oid, concurrency);
    }
    /**
       Runs method <code> %Open </code> in Cache to open an object
       from Cache database and creates corresponding object of class
       <code>IO.InternalIO</code>.

       @return <code> RegisteredObject </code>, corresponding to opened
       object. This object may be of <code>IO.InternalIO</code> or of
      any of its subclasses. Cast to <code>IO.InternalIO</code> is
      guaranteed to pass without <code>ClassCastException</code> exception.

       @param db <code>Database</code> object used for connection with
       Cache database.
       @param oid Object ID as specified in Cache. represented as
      <code>Oid</code>.
      @param concurrency Concurrency level.  represented as
      <code>Concurrency</code>.

      Here are concurrency values, see Object Concurrency Options in your on-line Cache' documentation for more information.
      @see <a href = "http://cache-server:57772/csp/documatic/DocBook.UI.Page.cls?KEY=GOBJ_concurrency"> Object Concurrency Options.</A>

      <TABLE border="1"
      summary="Object Concurrency Options.">
      <CAPTION><EM>Object Concurrency Options</EM></CAPTION>
      <TR><TD>-1 </TD><TD>Default concurrency</TD></TR>
      <TR><TD>0 </TD><TD>No locking, no locks are used</TD></TR>
      <TR><TD>1 </TD><TD>Atomic</TD></TR>
      <TR><TD>2 </TD><TD>Shared</TD></TR>
      <TR><TD>3 </TD><TD>Shared/Retained</TD></TR>
      <TR><TD>4 </TD><TD>Exclusive</TD></TR>
      </TABLE>

      @throws com.intersys.objects.CacheException in case of error.
      @see java.lang.ClassCastException
           @see #_open(com.intersys.objects.Database, com.intersys.objects.Oid)
           @see #open(com.intersys.objects.Database, com.intersys.objects.Oid)
           @see #InternalIO(com.intersys.objects.Database)
     */
    public static com.intersys.classes.RegisteredObject open (com.intersys.objects.Database db, com.intersys.objects.Oid oid, int concurrency) throws com.intersys.objects.CacheException {
        com.intersys.cache.CacheObject cobj = (((com.intersys.cache.SysDatabase)db).openCacheObject(CACHE_CLASS_NAME, oid.getData(), concurrency));
        return (com.intersys.classes.RegisteredObject)(cobj.newJavaInstance());
    }
    /**
    * Calls method
    * {@link com.intersys.objects.Database#openByQuery(String, String, Object[])}
    * to open all instances of class IO.InternalIO
    * that satisfy given simple
    * condition, specified in SQL syntax.
    *
    * @param db <code>Database</code> object used for connection with
    * Cache database.
    *
    * @param condition Simple condition on objects, in SQL syntax. It
    * can refer only to properties of a single object.
    *
    * @param args Parameters to replace "?" in
    * <code>condition</code>. If there are no parameters in condition
    * may be null or empty array.
    *
    * @return Iterator in the form of
    * <code>java.util.Iterator</code>, that iterates over all opened
    * objects in specified order.
    *
    * @see #openByQuery(com.intersys.objects.Database, String)
    * @see com.intersys.objects.Database#openByQuery(String, String, Object[])
    * @see com.intersys.objects.Database#openByQuery(String, String)
    * @see com.intersys.objects.Database#openByQuery(String, Object[])
    * @see com.intersys.objects.Database#openByQuery(String)
    *
    */
    public static java.util.Iterator openByQuery (com.intersys.objects.Database db, String condition, Object[] args) throws com.intersys.objects.CacheException {
        return db.openByQuery (CACHE_CLASS_NAME, condition, args);
    }
    /**
    * Calls method
    * {@link com.intersys.objects.Database#openByQuery(String, String)}
    * to open all instances of class Sample.Person
    * that satisfy given simple
    * condition, specified in SQL syntax.
    *
    * @param db <code>Database</code> object used for connection with
    * Cache database.
    *
    * @param condition Simple condition on objects, in SQL syntax. It
    * can refer only to properties of a single object.
    *
    * @return Iterator in the form of
    * <code>java.util.Iterator</code>, that iterates over all opened
    * objects in specified order.
    *
    * @see #openByQuery(com.intersys.objects.Database, String, Object[])
    * @see com.intersys.objects.Database#openByQuery(String, String)
    * @see com.intersys.objects.Database#openByQuery(String, String, Object[])
    * @see com.intersys.objects.Database#openByQuery(String, Object[])
    * @see com.intersys.objects.Database#openByQuery(String)
    *
    */
    public static java.util.Iterator openByQuery (com.intersys.objects.Database db, String condition) throws com.intersys.objects.CacheException {
        return db.openByQuery (CACHE_CLASS_NAME, condition);
    }
    /**
    * Calls method
    * {@link com.intersys.objects.Database#saveObjects(String,
    java.util.Collection)}
    * to save a collection of previously created new objects in Cache
    * Database. All object in
    * collection must already exist in database. All
    * objects must be instances of <code>IO.InternalIO</code> and not of its
    * subclasses. To save
    * changes in newly created objects use <code>createObjects</code>
    * method or instance method <code>save</code> on the object
    * itself.
    *
    * @param db <code>Database</code> object used for connection with
    * Cache database.
    *
    * @param objects A collection of newly created objects. None of
    * the objects in the collection may already exist in database.
    *
    * @see #saveObjects(com.intersys.objects.Database, java.util.Collection)
    * @see com.intersys.objects.Database#createObjects(String, java.util.Collection)
    *
    */
    public static void saveObjects (com.intersys.objects.Database db, java.util.Collection objects) throws com.intersys.objects.CacheException {
        db.saveObjects (CACHE_CLASS_NAME, objects);
    }
    /**
       Runs method <code> %Delete </code> in Cache to delete an object
       from Cache database.

       Deletes the stored version of the object with OID <var>oid</var> from the database.
       It does not remove any in-memory versions of the object that may be present.

       @param db <code>Database</code> object used for connection with
       Cache database.

       @param id ID as specified in Cache represented as
       <code>Id</code>.

       @throws com.intersys.objects.CacheException in case of error.
      @see java.lang.ClassCastException
           @see #_deleteId(com.intersys.objects.Database, com.intersys.objects.Id)
           @see #InternalIO
     */
    public static void delete (com.intersys.objects.Database db, com.intersys.objects.Id id) throws com.intersys.objects.CacheException {
        ((com.intersys.cache.SysDatabase)db).deleteObject(CACHE_CLASS_NAME, id);
    }
    /**
       Runs method <code> %Delete </code> in Cache to delete an object
       from Cache database.

       Deletes the stored version of the object with OID <var>oid</var> from the database.
       It does not remove any in-memory versions of the object that may be present.

       @param db <code>Database</code> object used for connection with
       Cache database.

       @param id ID as specified in Cache represented as
       <code>Id</code>.
       @param concurrency Concurrency level.  represented as
       <code>Concurrency</code>.

      Here are concurrency values, see Object Concurrency Options in your on-line Cache' documentation for more information.
      @see <a href = "http://cache-server:57772/csp/documatic/DocBook.UI.Page.cls?KEY=GOBJ_concurrency"> Object Concurrency Options.</A>

      <TABLE border="1"
      summary="Object Concurrency Options.">
      <CAPTION><EM>Object Concurrency Options</EM></CAPTION>
      <TR><TD>-1 </TD><TD>Default concurrency</TD></TR>
      <TR><TD>0 </TD><TD>No locking, no locks are used</TD></TR>
      <TR><TD>1 </TD><TD>Atomic</TD></TR>
      <TR><TD>2 </TD><TD>Shared</TD></TR>
      <TR><TD>3 </TD><TD>Shared/Retained</TD></TR>
      <TR><TD>4 </TD><TD>Exclusive</TD></TR>
      </TABLE>


       @throws com.intersys.objects.CacheException in case of error.
      @see java.lang.ClassCastException
           @see #_deleteId(com.intersys.objects.Database, com.intersys.objects.Id)
           @see #InternalIO
     */
    public static void delete (com.intersys.objects.Database db, com.intersys.objects.Id id, int concurrency) throws com.intersys.objects.CacheException {
        ((com.intersys.cache.SysDatabase)db).deleteObject(CACHE_CLASS_NAME, id, concurrency);
    }
    /**
       Runs method <code> %Delete </code> in Cache to delete an object
       from Cache database.

       Deletes the stored version of the object with OID <var>oid</var> from the database.
       It does not remove any in-memory versions of the object that may be present.

       @param db <code>Database</code> object used for connection with
       Cache database.

       @param id ID as specified in Cache represented as
       <code>Id</code>.

       @throws com.intersys.objects.CacheException in case of error.
      @see java.lang.ClassCastException
           @see #_deleteId(com.intersys.objects.Database, com.intersys.objects.Id)
           @see #InternalIO
     */
    public static void _deleteId (com.intersys.objects.Database db, com.intersys.objects.Id id) throws com.intersys.objects.CacheException {
        delete(db, id);
    }
    /**
       Runs method <code> %Delete </code> in Cache to delete an object
       from Cache database.

       Deletes the stored version of the object with OID <var>oid</var> from the database.
       It does not remove any in-memory versions of the object that may be present.

       @param db <code>Database</code> object used for connection with
       Cache database.

       @param id ID as specified in Cache represented as
       <code>Id</code>.
       @param concurrency Concurrency level.  represented as
       <code>Concurrency</code>.

      Here are concurrency values, see Object Concurrency Options in your on-line Cache' documentation for more information.
      @see <a href = "http://cache-server:57772/csp/documatic/DocBook.UI.Page.cls?KEY=GOBJ_concurrency"> Object Concurrency Options.</A>

      <TABLE border="1"
      summary="Object Concurrency Options.">
      <CAPTION><EM>Object Concurrency Options</EM></CAPTION>
      <TR><TD>-1 </TD><TD>Default concurrency</TD></TR>
      <TR><TD>0 </TD><TD>No locking, no locks are used</TD></TR>
      <TR><TD>1 </TD><TD>Atomic</TD></TR>
      <TR><TD>2 </TD><TD>Shared</TD></TR>
      <TR><TD>3 </TD><TD>Shared/Retained</TD></TR>
      <TR><TD>4 </TD><TD>Exclusive</TD></TR>
      </TABLE>


       @throws com.intersys.objects.CacheException in case of error.
      @see java.lang.ClassCastException
           @see #_deleteId(com.intersys.objects.Database, com.intersys.objects.Id)
           @see #InternalIO
     */
    public static void _deleteId (com.intersys.objects.Database db, com.intersys.objects.Id id, int concurrency) throws com.intersys.objects.CacheException {
        delete(db, id, concurrency);
    }
    /**
       Runs method <code> %Exists </code> in Cache to see if an object exists.

       @param db <code>Database</code> object used for connection with
       Cache database.

       @param id ID as specified in Cache represented as
      <code>Id</code>.

       @throws com.intersys.objects.CacheException in case of error.
      @see java.lang.ClassCastException
           @see #_existsId(com.intersys.objects.Database, com.intersys.objects.Id)
           @see #InternalIO
     */
    public static boolean exists (com.intersys.objects.Database db, com.intersys.objects.Id id) throws com.intersys.objects.CacheException {
        return ((com.intersys.cache.SysDatabase)db).existsObject(CACHE_CLASS_NAME, id);
    }
    /**
       Runs method <code> %Exists </code> in Cache to see if an object exists.

       @param db <code>Database</code> object used for connection with
       Cache database.

       @param id ID as specified in Cache represented as
      <code>Id</code>.

       @throws com.intersys.objects.CacheException in case of error.
      @see java.lang.ClassCastException
           @see #_existsId(com.intersys.objects.Database, com.intersys.objects.Id)
           @see #InternalIO
     */
    public static Boolean _existsId (com.intersys.objects.Database db, com.intersys.objects.Id id) throws com.intersys.objects.CacheException {
        return new Boolean(exists(db, id));
    }
    /**
       Returns class name of the class IO.InternalIO as it is in
      Cache Database. Note, that this is a static method, so no
      object specific information can be returned. Use
      <code>getCacheClass().getName()</code> to get the class name
      for specific object.
       @return Cache class name as a <code>String</code>
      @see #getCacheClass()
      @see com.intersys.objects.reflect.CacheClass#getName()
     */
    public static String getCacheClassName( ) {
        return CACHE_CLASS_NAME;
    }

   /**
           Allows access metadata information about type of this object
           in Cache database. Also can be used for dynamic binding (accessing
           properties and calling methods without particular class known).

           @return <code>CacheClass</code> object for this object type.
   */
    public com.intersys.objects.reflect.CacheClass getCacheClass( ) throws com.intersys.objects.CacheException {
        return mInternal.getCacheClass();
    }

    /**
       Verifies that all fields from Cache class are exposed with
       accessor methods in Java class and that values for indexes in
       zObjVal are the same as in Cache. It does not return anything
       but it throws an exception in case of inconsistency.

       <p>But if there is any inconsistency in zObjVal indexes this is fatal and class can not work correctly and must be regenerated

       @param db Database used for connection. Note that if you are
       using multiple databases the class can be consistent with one
       and inconsistent with another.
       @throws com.intersys.objects.InvalidClassException if any inconsistency is found.
       @throws com.intersys.objects.CacheException if any error occurred during
       verification, e.g. communication error with Database.
       @see com.intersys.objects.InvalidPropertyException

     */
    public static void checkAllFieldsValid(com.intersys.objects.Database db ) throws com.intersys.objects.CacheException {
        checkAllFieldsValid(db, CACHE_CLASS_NAME, InternalIO.class);
    }

    /**
       Runs method <code> %Exists </code> in Cache to see if an object exists.

       @return <code> RegisteredObject </code>, corresponding to opened
       object. This object may be of <code>IO.InternalIO</code> or of
      any of its subclasses. Cast to <code>IO.InternalIO</code> is
      guaranteed to pass without <code>ClassCastException</code> exception.

       @param db <code>Database</code> object used for connection with
       Cache database.

       @param oid Object ID as specified in Cache. represented as
      <code>Oid</code>.

       @throws com.intersys.objects.CacheException in case of error.
      @see java.lang.ClassCastException
           @see #_open(com.intersys.objects.Database, com.intersys.objects.Oid)
           @see #open(com.intersys.objects.Database, com.intersys.objects.Oid)
           @see #InternalIO
     */
    public static boolean exists (com.intersys.objects.Database db, com.intersys.objects.Oid oid) throws com.intersys.objects.CacheException {
        return exists (db, oid, CACHE_CLASS_NAME);
    }

    /**
       Verifies that all fields from Cache class are exposed with
       accessor methods in Java class and that values for indexes in
       zObjVal are the same as in Cache. It does not return anything
       but it throws an exception in case of inconsistency.

       <p>But if there is any inconsistency in zObjVal indexes this is fatal and class can not work correctly and must be regenerated

       @param db Database used for connection. Note that if you are
       using multiple databases the class can be consistent with one
       and inconsistent with another.
       @throws com.intersys.objects.InvalidClassException if any inconsistency is found.
       @throws com.intersys.objects.CacheException if any error occurred during
       verification, e.g. communication error with Database.
       @see com.intersys.objects.InvalidPropertyException

     */
    public static void checkAllMethods(com.intersys.objects.Database db ) throws com.intersys.objects.CacheException {
        checkAllMethods(db, CACHE_CLASS_NAME, InternalIO.class);
    }
    private static int ii_Close = 13;
    private static int jj_Close = 0;
    private static int kk_Close = 13;
    /**
       Verifies that indexes for property <code>Close</code> in
       zObjVal are the same as in Cache. It does not return anything
       but it throws an exception in case of inconsistency.

       <p> Please note, that if there is any inconsistency in zObjVal
       indexes this is fatal and class can not work correctly and must
       be regenerated.

       @param db Database used for connection. Note that if you are
       using multiple databases the class can be consistent with one
       and inconsistent with another.
       @throws com.intersys.objects.InvalidClassException if any inconsistency is found.
       @throws com.intersys.objects.CacheException if any error occurred during
       verification, e.g. communication error with Database.
       @see #checkAllFieldsValid

     */
    public static void checkCloseValid (com.intersys.objects.Database db) throws com.intersys.objects.CacheException {
        checkZobjValid(db, CACHE_CLASS_NAME, "Close",ii_Close, jj_Close, kk_Close);
    }
    /**
       Returns value of property <code>Close</code>.
       <Description>
       @return current value of <code>Close</code> represented as
       <code>java.lang.Double</code>

       @throws com.intersys.objects.CacheException if any error occurred during value retrieval.
       @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#Close"> Close</A>
    */
    public java.lang.Double getClose()  throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder dh = mInternal.getProperty(ii_Close,
                                                jj_Close,
                                                com.intersys.objects.Database.RET_PRIM,
                                                "Close");
       return dh.getDouble();
    }

    /**
       Sets new value for <code>Close</code>.
       <Description>
       @param value new value to be set represented as
       <code>java.lang.Double</code>.
       @throws com.intersys.objects.CacheException if any error occurred during value setting.
       @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#Close"> Close</A>
    */
    public void setClose(java.lang.Double value)  throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder dh = new com.intersys.cache.Dataholder (value);
        mInternal.setProperty(ii_Close, jj_Close,kk_Close, com.intersys.objects.Database.RET_PRIM, "Close", dh);
        return;
    }

    private static int ii_CreatedDate = 14;
    private static int jj_CreatedDate = 0;
    private static int kk_CreatedDate = 14;
    /**
       Verifies that indexes for property <code>CreatedDate</code> in
       zObjVal are the same as in Cache. It does not return anything
       but it throws an exception in case of inconsistency.

       <p> Please note, that if there is any inconsistency in zObjVal
       indexes this is fatal and class can not work correctly and must
       be regenerated.

       @param db Database used for connection. Note that if you are
       using multiple databases the class can be consistent with one
       and inconsistent with another.
       @throws com.intersys.objects.InvalidClassException if any inconsistency is found.
       @throws com.intersys.objects.CacheException if any error occurred during
       verification, e.g. communication error with Database.
       @see #checkAllFieldsValid

     */
    public static void checkCreatedDateValid (com.intersys.objects.Database db) throws com.intersys.objects.CacheException {
        checkZobjValid(db, CACHE_CLASS_NAME, "CreatedDate",ii_CreatedDate, jj_CreatedDate, kk_CreatedDate);
    }
    /**
       Returns value of property <code>CreatedDate</code>.
       <Description>
       @return current value of <code>CreatedDate</code> represented as
       <code>java.sql.Date</code>

       @throws com.intersys.objects.CacheException if any error occurred during value retrieval.
       @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#CreatedDate"> CreatedDate</A>
    */
    public java.sql.Date getCreatedDate()  throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder dh = mInternal.getProperty(ii_CreatedDate,
                                                jj_CreatedDate,
                                                com.intersys.objects.Database.RET_PRIM,
                                                "CreatedDate");
       return dh.getDate();
    }

    /**
       Sets new value for <code>CreatedDate</code>.
       <Description>
       @param value new value to be set represented as
       <code>java.sql.Date</code>.
       @throws com.intersys.objects.CacheException if any error occurred during value setting.
       @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#CreatedDate"> CreatedDate</A>
    */
    public void setCreatedDate(java.sql.Date value)  throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder dh = new com.intersys.cache.Dataholder (value);
        mInternal.setProperty(ii_CreatedDate, jj_CreatedDate,kk_CreatedDate, com.intersys.objects.Database.RET_PRIM, "CreatedDate", dh);
        return;
    }

    private static int ii_CreatedTime = 3;
    private static int jj_CreatedTime = 0;
    private static int kk_CreatedTime = 3;
    /**
       Verifies that indexes for property <code>CreatedTime</code> in
       zObjVal are the same as in Cache. It does not return anything
       but it throws an exception in case of inconsistency.

       <p> Please note, that if there is any inconsistency in zObjVal
       indexes this is fatal and class can not work correctly and must
       be regenerated.

       @param db Database used for connection. Note that if you are
       using multiple databases the class can be consistent with one
       and inconsistent with another.
       @throws com.intersys.objects.InvalidClassException if any inconsistency is found.
       @throws com.intersys.objects.CacheException if any error occurred during
       verification, e.g. communication error with Database.
       @see #checkAllFieldsValid

     */
    public static void checkCreatedTimeValid (com.intersys.objects.Database db) throws com.intersys.objects.CacheException {
        checkZobjValid(db, CACHE_CLASS_NAME, "CreatedTime",ii_CreatedTime, jj_CreatedTime, kk_CreatedTime);
    }
    /**
       Returns value of property <code>CreatedTime</code>.
       <Description>
       @return current value of <code>CreatedTime</code> represented as
       <code>java.sql.Time</code>

       @throws com.intersys.objects.CacheException if any error occurred during value retrieval.
       @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#CreatedTime"> CreatedTime</A>
    */
    public java.sql.Time getCreatedTime()  throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder dh = mInternal.getProperty(ii_CreatedTime,
                                                jj_CreatedTime,
                                                com.intersys.objects.Database.RET_PRIM,
                                                "CreatedTime");
       return dh.getTime();
    }

    /**
       Sets new value for <code>CreatedTime</code>.
       <Description>
       @param value new value to be set represented as
       <code>java.sql.Time</code>.
       @throws com.intersys.objects.CacheException if any error occurred during value setting.
       @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#CreatedTime"> CreatedTime</A>
    */
    public void setCreatedTime(java.sql.Time value)  throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder dh = new com.intersys.cache.Dataholder (value);
        mInternal.setProperty(ii_CreatedTime, jj_CreatedTime,kk_CreatedTime, com.intersys.objects.Database.RET_PRIM, "CreatedTime", dh);
        return;
    }

    private static int ii_EventNew = 8;
    private static int jj_EventNew = 0;
    private static int kk_EventNew = 8;
    /**
       Verifies that indexes for property <code>EventNew</code> in
       zObjVal are the same as in Cache. It does not return anything
       but it throws an exception in case of inconsistency.

       <p> Please note, that if there is any inconsistency in zObjVal
       indexes this is fatal and class can not work correctly and must
       be regenerated.

       @param db Database used for connection. Note that if you are
       using multiple databases the class can be consistent with one
       and inconsistent with another.
       @throws com.intersys.objects.InvalidClassException if any inconsistency is found.
       @throws com.intersys.objects.CacheException if any error occurred during
       verification, e.g. communication error with Database.
       @see #checkAllFieldsValid

     */
    public static void checkEventNewValid (com.intersys.objects.Database db) throws com.intersys.objects.CacheException {
        checkZobjValid(db, CACHE_CLASS_NAME, "EventNew",ii_EventNew, jj_EventNew, kk_EventNew);
    }
    /**
       Returns value of property <code>EventNew</code>.
       <Description>
       @return current value of <code>EventNew</code> represented as
       <code>com.intersys.classes.RelationshipObject</code>

       @throws com.intersys.objects.CacheException if any error occurred during value retrieval.
       @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#EventNew"> EventNew</A>
    */
    public com.intersys.classes.RelationshipObject getEventNew()  throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder dh = mInternal.getProperty(ii_EventNew,
                                                jj_EventNew,
                                                com.intersys.objects.Database.RET_OBJECT,
                                                "EventNew");
        com.intersys.cache.CacheObject cobj = dh.getCacheObject();
        if (cobj == null)
            return null;
        return (com.intersys.classes.RelationshipObject)(cobj.newJavaInstance());
    }

    private static int ii_EventOld = 4;
    private static int jj_EventOld = 0;
    private static int kk_EventOld = 4;
    /**
       Verifies that indexes for property <code>EventOld</code> in
       zObjVal are the same as in Cache. It does not return anything
       but it throws an exception in case of inconsistency.

       <p> Please note, that if there is any inconsistency in zObjVal
       indexes this is fatal and class can not work correctly and must
       be regenerated.

       @param db Database used for connection. Note that if you are
       using multiple databases the class can be consistent with one
       and inconsistent with another.
       @throws com.intersys.objects.InvalidClassException if any inconsistency is found.
       @throws com.intersys.objects.CacheException if any error occurred during
       verification, e.g. communication error with Database.
       @see #checkAllFieldsValid

     */
    public static void checkEventOldValid (com.intersys.objects.Database db) throws com.intersys.objects.CacheException {
        checkZobjValid(db, CACHE_CLASS_NAME, "EventOld",ii_EventOld, jj_EventOld, kk_EventOld);
    }
    /**
       Returns value of property <code>EventOld</code>.
       <Description>
       @return current value of <code>EventOld</code> represented as
       <code>com.intersys.classes.RelationshipObject</code>

       @throws com.intersys.objects.CacheException if any error occurred during value retrieval.
       @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#EventOld"> EventOld</A>
    */
    public com.intersys.classes.RelationshipObject getEventOld()  throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder dh = mInternal.getProperty(ii_EventOld,
                                                jj_EventOld,
                                                com.intersys.objects.Database.RET_OBJECT,
                                                "EventOld");
        com.intersys.cache.CacheObject cobj = dh.getCacheObject();
        if (cobj == null)
            return null;
        return (com.intersys.classes.RelationshipObject)(cobj.newJavaInstance());
    }

    private static int ii_High = 5;
    private static int jj_High = 0;
    private static int kk_High = 5;
    /**
       Verifies that indexes for property <code>High</code> in
       zObjVal are the same as in Cache. It does not return anything
       but it throws an exception in case of inconsistency.

       <p> Please note, that if there is any inconsistency in zObjVal
       indexes this is fatal and class can not work correctly and must
       be regenerated.

       @param db Database used for connection. Note that if you are
       using multiple databases the class can be consistent with one
       and inconsistent with another.
       @throws com.intersys.objects.InvalidClassException if any inconsistency is found.
       @throws com.intersys.objects.CacheException if any error occurred during
       verification, e.g. communication error with Database.
       @see #checkAllFieldsValid

     */
    public static void checkHighValid (com.intersys.objects.Database db) throws com.intersys.objects.CacheException {
        checkZobjValid(db, CACHE_CLASS_NAME, "High",ii_High, jj_High, kk_High);
    }
    /**
       Returns value of property <code>High</code>.
       <Description>
       @return current value of <code>High</code> represented as
       <code>java.lang.Double</code>

       @throws com.intersys.objects.CacheException if any error occurred during value retrieval.
       @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#High"> High</A>
    */
    public java.lang.Double getHigh()  throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder dh = mInternal.getProperty(ii_High,
                                                jj_High,
                                                com.intersys.objects.Database.RET_PRIM,
                                                "High");
       return dh.getDouble();
    }

    /**
       Sets new value for <code>High</code>.
       <Description>
       @param value new value to be set represented as
       <code>java.lang.Double</code>.
       @throws com.intersys.objects.CacheException if any error occurred during value setting.
       @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#High"> High</A>
    */
    public void setHigh(java.lang.Double value)  throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder dh = new com.intersys.cache.Dataholder (value);
        mInternal.setProperty(ii_High, jj_High,kk_High, com.intersys.objects.Database.RET_PRIM, "High", dh);
        return;
    }

    private static int ii_Low = 6;
    private static int jj_Low = 0;
    private static int kk_Low = 6;
    /**
       Verifies that indexes for property <code>Low</code> in
       zObjVal are the same as in Cache. It does not return anything
       but it throws an exception in case of inconsistency.

       <p> Please note, that if there is any inconsistency in zObjVal
       indexes this is fatal and class can not work correctly and must
       be regenerated.

       @param db Database used for connection. Note that if you are
       using multiple databases the class can be consistent with one
       and inconsistent with another.
       @throws com.intersys.objects.InvalidClassException if any inconsistency is found.
       @throws com.intersys.objects.CacheException if any error occurred during
       verification, e.g. communication error with Database.
       @see #checkAllFieldsValid

     */
    public static void checkLowValid (com.intersys.objects.Database db) throws com.intersys.objects.CacheException {
        checkZobjValid(db, CACHE_CLASS_NAME, "Low",ii_Low, jj_Low, kk_Low);
    }
    /**
       Returns value of property <code>Low</code>.
       <Description>
       @return current value of <code>Low</code> represented as
       <code>java.lang.Double</code>

       @throws com.intersys.objects.CacheException if any error occurred during value retrieval.
       @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#Low"> Low</A>
    */
    public java.lang.Double getLow()  throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder dh = mInternal.getProperty(ii_Low,
                                                jj_Low,
                                                com.intersys.objects.Database.RET_PRIM,
                                                "Low");
       return dh.getDouble();
    }

    /**
       Sets new value for <code>Low</code>.
       <Description>
       @param value new value to be set represented as
       <code>java.lang.Double</code>.
       @throws com.intersys.objects.CacheException if any error occurred during value setting.
       @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#Low"> Low</A>
    */
    public void setLow(java.lang.Double value)  throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder dh = new com.intersys.cache.Dataholder (value);
        mInternal.setProperty(ii_Low, jj_Low,kk_Low, com.intersys.objects.Database.RET_PRIM, "Low", dh);
        return;
    }

    private static int ii_Open = 15;
    private static int jj_Open = 0;
    private static int kk_Open = 15;
    /**
       Verifies that indexes for property <code>Open</code> in
       zObjVal are the same as in Cache. It does not return anything
       but it throws an exception in case of inconsistency.

       <p> Please note, that if there is any inconsistency in zObjVal
       indexes this is fatal and class can not work correctly and must
       be regenerated.

       @param db Database used for connection. Note that if you are
       using multiple databases the class can be consistent with one
       and inconsistent with another.
       @throws com.intersys.objects.InvalidClassException if any inconsistency is found.
       @throws com.intersys.objects.CacheException if any error occurred during
       verification, e.g. communication error with Database.
       @see #checkAllFieldsValid

     */
    public static void checkOpenValid (com.intersys.objects.Database db) throws com.intersys.objects.CacheException {
        checkZobjValid(db, CACHE_CLASS_NAME, "Open",ii_Open, jj_Open, kk_Open);
    }
    /**
       Returns value of property <code>Open</code>.
       <Description>
       @return current value of <code>Open</code> represented as
       <code>java.lang.Double</code>

       @throws com.intersys.objects.CacheException if any error occurred during value retrieval.
       @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#Open"> Open</A>
    */
    public java.lang.Double getOpen()  throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder dh = mInternal.getProperty(ii_Open,
                                                jj_Open,
                                                com.intersys.objects.Database.RET_PRIM,
                                                "Open");
       return dh.getDouble();
    }

    /**
       Sets new value for <code>Open</code>.
       <Description>
       @param value new value to be set represented as
       <code>java.lang.Double</code>.
       @throws com.intersys.objects.CacheException if any error occurred during value setting.
       @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#Open"> Open</A>
    */
    public void setOpen(java.lang.Double value)  throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder dh = new com.intersys.cache.Dataholder (value);
        mInternal.setProperty(ii_Open, jj_Open,kk_Open, com.intersys.objects.Database.RET_PRIM, "Open", dh);
        return;
    }

    private static int ii_Ticker = 7;
    private static int jj_Ticker = 0;
    private static int kk_Ticker = 7;
    /**
       Verifies that indexes for property <code>Ticker</code> in
       zObjVal are the same as in Cache. It does not return anything
       but it throws an exception in case of inconsistency.

       <p> Please note, that if there is any inconsistency in zObjVal
       indexes this is fatal and class can not work correctly and must
       be regenerated.

       @param db Database used for connection. Note that if you are
       using multiple databases the class can be consistent with one
       and inconsistent with another.
       @throws com.intersys.objects.InvalidClassException if any inconsistency is found.
       @throws com.intersys.objects.CacheException if any error occurred during
       verification, e.g. communication error with Database.
       @see #checkAllFieldsValid

     */
    public static void checkTickerValid (com.intersys.objects.Database db) throws com.intersys.objects.CacheException {
        checkZobjValid(db, CACHE_CLASS_NAME, "Ticker",ii_Ticker, jj_Ticker, kk_Ticker);
    }
    /**
       Returns value of property <code>Ticker</code>.
       <Description>
       @return current value of <code>Ticker</code> represented as
       <code>Resources.Tickers</code>

       @throws com.intersys.objects.CacheException if any error occurred during value retrieval.
       @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#Ticker"> Ticker</A>
    */
    public Resources.Tickers getTicker()  throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder dh = mInternal.getProperty(ii_Ticker,
                                                jj_Ticker,
                                                com.intersys.objects.Database.RET_OBJECT,
                                                "Ticker");
        com.intersys.cache.CacheObject cobj = dh.getCacheObject();
        if (cobj == null)
            return null;
        return (Resources.Tickers)(cobj.newJavaInstance());
    }

    /**
       Sets new value for <code>Ticker</code>.
       <Description>
       @param value new value to be set represented as
       <code>Resources.Tickers</code>.
       @throws com.intersys.objects.CacheException if any error occurred during value setting.
       @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#Ticker"> Ticker</A>
    */
    public void setTicker(Resources.Tickers value)  throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder dh = new com.intersys.cache.Dataholder (value);
        mInternal.setProperty(ii_Ticker, jj_Ticker,kk_Ticker, com.intersys.objects.Database.RET_OBJECT, "Ticker", dh);
        return;
    }

    private static int ii_Volume = 10;
    private static int jj_Volume = 0;
    private static int kk_Volume = 10;
    /**
       Verifies that indexes for property <code>Volume</code> in
       zObjVal are the same as in Cache. It does not return anything
       but it throws an exception in case of inconsistency.

       <p> Please note, that if there is any inconsistency in zObjVal
       indexes this is fatal and class can not work correctly and must
       be regenerated.

       @param db Database used for connection. Note that if you are
       using multiple databases the class can be consistent with one
       and inconsistent with another.
       @throws com.intersys.objects.InvalidClassException if any inconsistency is found.
       @throws com.intersys.objects.CacheException if any error occurred during
       verification, e.g. communication error with Database.
       @see #checkAllFieldsValid

     */
    public static void checkVolumeValid (com.intersys.objects.Database db) throws com.intersys.objects.CacheException {
        checkZobjValid(db, CACHE_CLASS_NAME, "Volume",ii_Volume, jj_Volume, kk_Volume);
    }
    /**
       Returns value of property <code>Volume</code>.
       <Description>
       @return current value of <code>Volume</code> represented as
       <code>java.lang.Double</code>

       @throws com.intersys.objects.CacheException if any error occurred during value retrieval.
       @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#Volume"> Volume</A>
    */
    public java.lang.Double getVolume()  throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder dh = mInternal.getProperty(ii_Volume,
                                                jj_Volume,
                                                com.intersys.objects.Database.RET_PRIM,
                                                "Volume");
       return dh.getDouble();
    }

    /**
       Sets new value for <code>Volume</code>.
       <Description>
       @param value new value to be set represented as
       <code>java.lang.Double</code>.
       @throws com.intersys.objects.CacheException if any error occurred during value setting.
       @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#Volume"> Volume</A>
    */
    public void setVolume(java.lang.Double value)  throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder dh = new com.intersys.cache.Dataholder (value);
        mInternal.setProperty(ii_Volume, jj_Volume,kk_Volume, com.intersys.objects.Database.RET_PRIM, "Volume", dh);
        return;
    }

    /**
     <p>Runs method %ClassName in Cache.</p>
     <p>Description: Returns the object's class name. The <var>fullname</var> determines how the
class name is represented. If it is 1 then it returns the full class name
including any package qualifier. If it is 0 (the default) then it returns the
name of the class without the package, this is mainly for backward compatibility
with the pre-package behaviour of %ClassName.</p>
     @param db represented as com.intersys.objects.Database
     @param fullname represented as java.lang.Boolean
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#%ClassName"> Method %ClassName</A>
    */
    public static java.lang.String sys_ClassName (com.intersys.objects.Database db, java.lang.Boolean fullname) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(fullname);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%ClassName",args,com.intersys.objects.Database.RET_PRIM);
        return res.getString();
    }
    /**
     <p>Runs method %ComposeOid in Cache.</p>
     <p>Description: %ComposeOid()

		This class method accepts an ID and returns a fully qualified OID, containing the most specific type class of the object identified by the
		<var>id</var> argument. If default storage is used then the %%CLASSNAME value is used to determine the most specific type class, otherwise if
		%OnDetermineClass is runnable then it is called to determine the most specific type class. If neither case is true then the current class is
		used. If there is a problem determining the most specific type class then null ("") is returned.</p>
     @param db represented as com.intersys.objects.Database
     @param id represented as java.lang.String
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#%ComposeOid"> Method %ComposeOid</A>
    */
    public static void sys_ComposeOid (com.intersys.objects.Database db, java.lang.String id) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(id);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%ComposeOid",args,com.intersys.objects.Database.RET_NONE);
        return;
    }
    /**
     <p>Runs method %Delete in Cache.</p>
     <p>Description: Deletes the stored version of the object with OID <var>oid</var> from the database. 
It does not remove any in-memory versions of the object that may be present.

Refer to <LINK href=/AboutConcurrency.html>About Concurrency</LINK> for more details 
on the optional <var>concurrency</var> argument.

<p>Returns a <CLASS>%Status</CLASS> value indicating success or failure.

<p>Internally, <METHOD>%Delete</METHOD> initiates a transaction and then invokes the storage 
interface method <METHOD>%DeleteData</METHOD>. If <METHOD>%DeleteData</METHOD> succeeds, the 
transaction is committed, otherwise it is rolled back. </p>
     @param db represented as com.intersys.objects.Database
     default argument oid set to ""
     default argument concurrency set to -1
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see #sys_Delete(com.intersys.objects.Database,com.intersys.objects.Oid,java.lang.Integer)
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#%Delete"> Method %Delete</A>
    */
    public static void sys_Delete (com.intersys.objects.Database db) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[0];
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%Delete",args,com.intersys.objects.Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     <p>Runs method %Delete in Cache.</p>
     <p>Description: Deletes the stored version of the object with OID <var>oid</var> from the database. 
It does not remove any in-memory versions of the object that may be present.

Refer to <LINK href=/AboutConcurrency.html>About Concurrency</LINK> for more details 
on the optional <var>concurrency</var> argument.

<p>Returns a <CLASS>%Status</CLASS> value indicating success or failure.

<p>Internally, <METHOD>%Delete</METHOD> initiates a transaction and then invokes the storage 
interface method <METHOD>%DeleteData</METHOD>. If <METHOD>%DeleteData</METHOD> succeeds, the 
transaction is committed, otherwise it is rolled back. </p>
     @param db represented as com.intersys.objects.Database
     @param oid represented as com.intersys.objects.Oid
     default argument concurrency set to -1
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see #sys_Delete(com.intersys.objects.Database,com.intersys.objects.Oid,java.lang.Integer)
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#%Delete"> Method %Delete</A>
    */
    public static void sys_Delete (com.intersys.objects.Database db, com.intersys.objects.Oid oid) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(oid);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%Delete",args,com.intersys.objects.Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     <p>Runs method %Delete in Cache.</p>
     <p>Description: Deletes the stored version of the object with OID <var>oid</var> from the database. 
It does not remove any in-memory versions of the object that may be present.

Refer to <LINK href=/AboutConcurrency.html>About Concurrency</LINK> for more details 
on the optional <var>concurrency</var> argument.

<p>Returns a <CLASS>%Status</CLASS> value indicating success or failure.

<p>Internally, <METHOD>%Delete</METHOD> initiates a transaction and then invokes the storage 
interface method <METHOD>%DeleteData</METHOD>. If <METHOD>%DeleteData</METHOD> succeeds, the 
transaction is committed, otherwise it is rolled back. </p>
     @param db represented as com.intersys.objects.Database
     @param oid represented as com.intersys.objects.Oid
     @param concurrency represented as java.lang.Integer
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#%Delete"> Method %Delete</A>
    */
    public static void sys_Delete (com.intersys.objects.Database db, com.intersys.objects.Oid oid, java.lang.Integer concurrency) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[2];
        args[0] = new com.intersys.cache.Dataholder(oid);
        args[1] = new com.intersys.cache.Dataholder(concurrency);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%Delete",args,com.intersys.objects.Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     <p>Runs method %DeleteExtent in Cache.</p>
     <p>Description: Delete all instances of this class from its extent. On exit <var>instancecount</var> 
contains the original number of instances while <var>deletecount</var> contains 
the number of instances actually deleted.

<p>Internally, <METHOD>%DeleteExtent</METHOD> iterates over the set of instances in the 
collection and invokes the <METHOD>%Delete</METHOD> method.

Refer to <LINK href=/AboutConcurrency.html>About Concurrency</LINK> for more details 
on the optional <var>concurrency</var> argument.

<p>Returns a <CLASS>%Status</CLASS> value indicating success or failure.</p>
     @param db represented as com.intersys.objects.Database
     @param concurrency represented as java.lang.Integer
     @param deletecount represented as com.intersys.objects.StringHolder
     @param instancecount represented as com.intersys.objects.StringHolder
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#%DeleteExtent"> Method %DeleteExtent</A>
    */
    public static void sys_DeleteExtent (com.intersys.objects.Database db, java.lang.Integer concurrency, com.intersys.objects.StringHolder deletecount, com.intersys.objects.StringHolder instancecount) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[3];
        int[] _refs = new int[2];
        args[0] = new com.intersys.cache.Dataholder(concurrency);
        args[1] = com.intersys.cache.Dataholder.create (deletecount.value);
        _refs[0] = 2;
        args[2] = com.intersys.cache.Dataholder.create (instancecount.value);
        _refs[1] = 3;
        com.intersys.cache.Dataholder[] res=db.runClassMethod(CACHE_CLASS_NAME,"%DeleteExtent",_refs,args,com.intersys.objects.Database.RET_PRIM);
        deletecount.set(res[1].getString());
        instancecount.set(res[2].getString());
        db.parseStatus(res[0]);
        return;
    }
    /**
     <p>Runs method %DeleteId in Cache.</p>
     <p>Description: Deletes the stored version of the object with ID <var>id</var> from the database. 

<p><METHOD>%DeleteId</METHOD> is identical in operation to the <METHOD>%Delete</METHOD> method except 
that it uses and Id value instead of an OID value to find an object.

Refer to <LINK href=/AboutConcurrency.html>About Concurrency</LINK> for more details 
on the optional <var>concurrency</var> argument.</p>
     @param db represented as com.intersys.objects.Database
     @param id represented as java.lang.String
     default argument concurrency set to -1
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see #sys_DeleteId(com.intersys.objects.Database,java.lang.String,java.lang.Integer)
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#%DeleteId"> Method %DeleteId</A>
    */
    public static void sys_DeleteId (com.intersys.objects.Database db, java.lang.String id) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(id);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%DeleteId",args,com.intersys.objects.Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     <p>Runs method %DeleteId in Cache.</p>
     <p>Description: Deletes the stored version of the object with ID <var>id</var> from the database. 

<p><METHOD>%DeleteId</METHOD> is identical in operation to the <METHOD>%Delete</METHOD> method except 
that it uses and Id value instead of an OID value to find an object.

Refer to <LINK href=/AboutConcurrency.html>About Concurrency</LINK> for more details 
on the optional <var>concurrency</var> argument.</p>
     @param db represented as com.intersys.objects.Database
     @param id represented as java.lang.String
     @param concurrency represented as java.lang.Integer
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#%DeleteId"> Method %DeleteId</A>
    */
    public static void sys_DeleteId (com.intersys.objects.Database db, java.lang.String id, java.lang.Integer concurrency) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[2];
        args[0] = new com.intersys.cache.Dataholder(id);
        args[1] = new com.intersys.cache.Dataholder(concurrency);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%DeleteId",args,com.intersys.objects.Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     <p>Runs method %Exists in Cache.</p>
     @param db represented as com.intersys.objects.Database
     default argument oid set to ""
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see #sys_Exists(com.intersys.objects.Database,com.intersys.objects.Oid)
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#%Exists"> Method %Exists</A>
    */
    public static java.lang.Boolean sys_Exists (com.intersys.objects.Database db) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[0];
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%Exists",args,com.intersys.objects.Database.RET_PRIM);
        return res.getBoolean();
    }
    /**
     <p>Runs method %Exists in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param oid represented as com.intersys.objects.Oid
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#%Exists"> Method %Exists</A>
    */
    public static java.lang.Boolean sys_Exists (com.intersys.objects.Database db, com.intersys.objects.Oid oid) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(oid);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%Exists",args,com.intersys.objects.Database.RET_PRIM);
        return res.getBoolean();
    }
    /**
     <p>Runs method %ExistsId in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param id represented as java.lang.String
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#%ExistsId"> Method %ExistsId</A>
    */
    public static java.lang.Boolean sys_ExistsId (com.intersys.objects.Database db, java.lang.String id) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(id);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%ExistsId",args,com.intersys.objects.Database.RET_PRIM);
        return res.getBoolean();
    }
    /**
     <p>Runs method %IsA in Cache.</p>
     <p>Description: Returns true (1) if instances of this class are also instances of the isclass parameter.
That is 'isclass' is a primary superclass of this object.</p>
     @param db represented as com.intersys.objects.Database
     @param isclass represented as java.lang.String
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#%IsA"> Method %IsA</A>
    */
    public static java.lang.Integer sys_IsA (com.intersys.objects.Database db, java.lang.String isclass) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(isclass);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%IsA",args,com.intersys.objects.Database.RET_PRIM);
        return res.getInteger();
    }
    /**
     <p>Runs method %KillExtent in Cache.</p>
     <p>Description: %KillExtent performs a physical kill of the extent. No constraints are enforced. This method should not be
used in place of %DeleteExtent. Only physical storage occupied by this extent will be killed. If this extent
is a subextent of another class then no data is killed. Indexes that originate with this extent will be killed.
%KillExtent will be called on any subextents and on any child extents (the extent of the type class of a
relationship whose cardinality = children is a 'child' extent) of this extent.

This method is not meant for production applications. It is meant to be a development utility to quickly clear
extent physical data.</p>
     @param db represented as com.intersys.objects.Database
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#%KillExtent"> Method %KillExtent</A>
    */
    public static void sys_KillExtent (com.intersys.objects.Database db) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[0];
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%KillExtent",args,com.intersys.objects.Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     <p>Runs method %SaveIndices in Cache.</p>
     <p>Description: Files the indices for all objects whose ID is in the range defined by <var>pStartId</var> and <var>pEndId</var>.
If <var>pEndId</var> is null then it defaults to <var>pStartId</var>. If <var>pStartId</var> is null then the
range is empty and no filing will occur.

If <var>lockExtent</var> is true then an extent lock will be acquired before the indices
are built. If the lock cannot be acquired then an error is returned. The lock is released
after the index filing is completed.



<p>Returns a <CLASS>%Status</CLASS> value indicating success or failure.</p>
     @param db represented as com.intersys.objects.Database
     default argument pStartId set to ""
     default argument pEndId set to ""
     default argument lockExtent set to 0
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see #sys_SaveIndices(com.intersys.objects.Database,java.lang.String,java.lang.String,java.lang.Boolean)
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#%SaveIndices"> Method %SaveIndices</A>
    */
    public static void sys_SaveIndices (com.intersys.objects.Database db) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[0];
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%SaveIndices",args,com.intersys.objects.Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     <p>Runs method %SaveIndices in Cache.</p>
     <p>Description: Files the indices for all objects whose ID is in the range defined by <var>pStartId</var> and <var>pEndId</var>.
If <var>pEndId</var> is null then it defaults to <var>pStartId</var>. If <var>pStartId</var> is null then the
range is empty and no filing will occur.

If <var>lockExtent</var> is true then an extent lock will be acquired before the indices
are built. If the lock cannot be acquired then an error is returned. The lock is released
after the index filing is completed.



<p>Returns a <CLASS>%Status</CLASS> value indicating success or failure.</p>
     @param db represented as com.intersys.objects.Database
     @param pStartId represented as java.lang.String
     default argument pEndId set to ""
     default argument lockExtent set to 0
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see #sys_SaveIndices(com.intersys.objects.Database,java.lang.String,java.lang.String,java.lang.Boolean)
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#%SaveIndices"> Method %SaveIndices</A>
    */
    public static void sys_SaveIndices (com.intersys.objects.Database db, java.lang.String pStartId) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(pStartId);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%SaveIndices",args,com.intersys.objects.Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     <p>Runs method %SaveIndices in Cache.</p>
     <p>Description: Files the indices for all objects whose ID is in the range defined by <var>pStartId</var> and <var>pEndId</var>.
If <var>pEndId</var> is null then it defaults to <var>pStartId</var>. If <var>pStartId</var> is null then the
range is empty and no filing will occur.

If <var>lockExtent</var> is true then an extent lock will be acquired before the indices
are built. If the lock cannot be acquired then an error is returned. The lock is released
after the index filing is completed.



<p>Returns a <CLASS>%Status</CLASS> value indicating success or failure.</p>
     @param db represented as com.intersys.objects.Database
     @param pStartId represented as java.lang.String
     @param pEndId represented as java.lang.String
     default argument lockExtent set to 0
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see #sys_SaveIndices(com.intersys.objects.Database,java.lang.String,java.lang.String,java.lang.Boolean)
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#%SaveIndices"> Method %SaveIndices</A>
    */
    public static void sys_SaveIndices (com.intersys.objects.Database db, java.lang.String pStartId, java.lang.String pEndId) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[2];
        args[0] = new com.intersys.cache.Dataholder(pStartId);
        args[1] = new com.intersys.cache.Dataholder(pEndId);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%SaveIndices",args,com.intersys.objects.Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     <p>Runs method %SaveIndices in Cache.</p>
     <p>Description: Files the indices for all objects whose ID is in the range defined by <var>pStartId</var> and <var>pEndId</var>.
If <var>pEndId</var> is null then it defaults to <var>pStartId</var>. If <var>pStartId</var> is null then the
range is empty and no filing will occur.

If <var>lockExtent</var> is true then an extent lock will be acquired before the indices
are built. If the lock cannot be acquired then an error is returned. The lock is released
after the index filing is completed.



<p>Returns a <CLASS>%Status</CLASS> value indicating success or failure.</p>
     @param db represented as com.intersys.objects.Database
     @param pStartId represented as java.lang.String
     @param pEndId represented as java.lang.String
     @param lockExtent represented as java.lang.Boolean
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#%SaveIndices"> Method %SaveIndices</A>
    */
    public static void sys_SaveIndices (com.intersys.objects.Database db, java.lang.String pStartId, java.lang.String pEndId, java.lang.Boolean lockExtent) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[3];
        args[0] = new com.intersys.cache.Dataholder(pStartId);
        args[1] = new com.intersys.cache.Dataholder(pEndId);
        args[2] = new com.intersys.cache.Dataholder(lockExtent);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"%SaveIndices",args,com.intersys.objects.Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     <p>Runs method CloseDisplayToLogical in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param _val represented as java.lang.String
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#CloseDisplayToLogical"> Method CloseDisplayToLogical</A>
    */
    public static java.lang.Double CloseDisplayToLogical (com.intersys.objects.Database db, java.lang.String _val) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(_val);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"CloseDisplayToLogical",args,com.intersys.objects.Database.RET_PRIM);
        return res.getDouble();
    }
    /**
     <p>Runs method CloseIsValid in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param _val represented as java.lang.String
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#CloseIsValid"> Method CloseIsValid</A>
    */
    public static void CloseIsValid (com.intersys.objects.Database db, java.lang.String _val) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(_val);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"CloseIsValid",args,com.intersys.objects.Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     <p>Runs method CloseLogicalToDisplay in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param _val represented as java.lang.Double
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#CloseLogicalToDisplay"> Method CloseLogicalToDisplay</A>
    */
    public static java.lang.String CloseLogicalToDisplay (com.intersys.objects.Database db, java.lang.Double _val) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(_val);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"CloseLogicalToDisplay",args,com.intersys.objects.Database.RET_PRIM);
        return res.getString();
    }
    /**
     <p>Runs method CreatedDateDisplayToLogical in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param _val represented as java.lang.String
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#CreatedDateDisplayToLogical"> Method CreatedDateDisplayToLogical</A>
    */
    public static java.sql.Date CreatedDateDisplayToLogical (com.intersys.objects.Database db, java.lang.String _val) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(_val);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"CreatedDateDisplayToLogical",args,com.intersys.objects.Database.RET_PRIM);
        return res.getDate();
    }
    /**
     <p>Runs method CreatedDateIsValid in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param _val represented as java.lang.String
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#CreatedDateIsValid"> Method CreatedDateIsValid</A>
    */
    public static void CreatedDateIsValid (com.intersys.objects.Database db, java.lang.String _val) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(_val);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"CreatedDateIsValid",args,com.intersys.objects.Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     <p>Runs method CreatedDateLogicalToDisplay in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param _val represented as java.sql.Date
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#CreatedDateLogicalToDisplay"> Method CreatedDateLogicalToDisplay</A>
    */
    public static java.lang.String CreatedDateLogicalToDisplay (com.intersys.objects.Database db, java.sql.Date _val) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(_val);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"CreatedDateLogicalToDisplay",args,com.intersys.objects.Database.RET_PRIM);
        return res.getString();
    }
    /**
     <p>Runs method CreatedTimeDisplayToLogical in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param _val represented as java.lang.String
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#CreatedTimeDisplayToLogical"> Method CreatedTimeDisplayToLogical</A>
    */
    public static java.sql.Time CreatedTimeDisplayToLogical (com.intersys.objects.Database db, java.lang.String _val) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(_val);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"CreatedTimeDisplayToLogical",args,com.intersys.objects.Database.RET_PRIM);
        return res.getTime();
    }
    /**
     <p>Runs method CreatedTimeIsValid in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param _val represented as java.lang.String
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#CreatedTimeIsValid"> Method CreatedTimeIsValid</A>
    */
    public static void CreatedTimeIsValid (com.intersys.objects.Database db, java.lang.String _val) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(_val);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"CreatedTimeIsValid",args,com.intersys.objects.Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     <p>Runs method CreatedTimeLogicalToDisplay in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param _val represented as java.sql.Time
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#CreatedTimeLogicalToDisplay"> Method CreatedTimeLogicalToDisplay</A>
    */
    public static java.lang.String CreatedTimeLogicalToDisplay (com.intersys.objects.Database db, java.sql.Time _val) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(_val);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"CreatedTimeLogicalToDisplay",args,com.intersys.objects.Database.RET_PRIM);
        return res.getString();
    }
    /**
     <p>Runs method EventNewGetObject in Cache.</p>
     default argument force set to 0
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see #EventNewGetObject(java.lang.Integer)
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#EventNewGetObject"> Method EventNewGetObject</A>
    */
    public com.intersys.objects.Oid EventNewGetObject () throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[0];
        com.intersys.cache.Dataholder res=mInternal.runInstanceMethod("EventNewGetObject",args,com.intersys.objects.Database.RET_PRIM);
        return res.getOid();
    }
    /**
     <p>Runs method EventNewGetObject in Cache.</p>
     @param force represented as java.lang.Integer
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#EventNewGetObject"> Method EventNewGetObject</A>
    */
    public com.intersys.objects.Oid EventNewGetObject (java.lang.Integer force) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(force);
        com.intersys.cache.Dataholder res=mInternal.runInstanceMethod("EventNewGetObject",args,com.intersys.objects.Database.RET_PRIM);
        return res.getOid();
    }
    /**
     <p>Runs method EventNewGetObjectId in Cache.</p>
     default argument force set to 0
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see #EventNewGetObjectId(java.lang.Integer)
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#EventNewGetObjectId"> Method EventNewGetObjectId</A>
    */
    public java.lang.String EventNewGetObjectId () throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[0];
        com.intersys.cache.Dataholder res=mInternal.runInstanceMethod("EventNewGetObjectId",args,com.intersys.objects.Database.RET_PRIM);
        return res.getString();
    }
    /**
     <p>Runs method EventNewGetObjectId in Cache.</p>
     @param force represented as java.lang.Integer
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#EventNewGetObjectId"> Method EventNewGetObjectId</A>
    */
    public java.lang.String EventNewGetObjectId (java.lang.Integer force) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(force);
        com.intersys.cache.Dataholder res=mInternal.runInstanceMethod("EventNewGetObjectId",args,com.intersys.objects.Database.RET_PRIM);
        return res.getString();
    }
    /**
     <p>Runs method EventNewIsValid in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param value represented as java.lang.String
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#EventNewIsValid"> Method EventNewIsValid</A>
    */
    public static void EventNewIsValid (com.intersys.objects.Database db, java.lang.String value) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(value);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"EventNewIsValid",args,com.intersys.objects.Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     <p>Runs method EventOldGetObject in Cache.</p>
     default argument force set to 0
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see #EventOldGetObject(java.lang.Integer)
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#EventOldGetObject"> Method EventOldGetObject</A>
    */
    public com.intersys.objects.Oid EventOldGetObject () throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[0];
        com.intersys.cache.Dataholder res=mInternal.runInstanceMethod("EventOldGetObject",args,com.intersys.objects.Database.RET_PRIM);
        return res.getOid();
    }
    /**
     <p>Runs method EventOldGetObject in Cache.</p>
     @param force represented as java.lang.Integer
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#EventOldGetObject"> Method EventOldGetObject</A>
    */
    public com.intersys.objects.Oid EventOldGetObject (java.lang.Integer force) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(force);
        com.intersys.cache.Dataholder res=mInternal.runInstanceMethod("EventOldGetObject",args,com.intersys.objects.Database.RET_PRIM);
        return res.getOid();
    }
    /**
     <p>Runs method EventOldGetObjectId in Cache.</p>
     default argument force set to 0
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see #EventOldGetObjectId(java.lang.Integer)
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#EventOldGetObjectId"> Method EventOldGetObjectId</A>
    */
    public java.lang.String EventOldGetObjectId () throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[0];
        com.intersys.cache.Dataholder res=mInternal.runInstanceMethod("EventOldGetObjectId",args,com.intersys.objects.Database.RET_PRIM);
        return res.getString();
    }
    /**
     <p>Runs method EventOldGetObjectId in Cache.</p>
     @param force represented as java.lang.Integer
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#EventOldGetObjectId"> Method EventOldGetObjectId</A>
    */
    public java.lang.String EventOldGetObjectId (java.lang.Integer force) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(force);
        com.intersys.cache.Dataholder res=mInternal.runInstanceMethod("EventOldGetObjectId",args,com.intersys.objects.Database.RET_PRIM);
        return res.getString();
    }
    /**
     <p>Runs method EventOldIsValid in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param value represented as java.lang.String
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#EventOldIsValid"> Method EventOldIsValid</A>
    */
    public static void EventOldIsValid (com.intersys.objects.Database db, java.lang.String value) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(value);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"EventOldIsValid",args,com.intersys.objects.Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     <p>Runs method GetFirst in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param ticker represented as java.lang.Integer
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#GetFirst"> Method GetFirst</A>
    */
    public static java.lang.Integer GetFirst (com.intersys.objects.Database db, java.lang.Integer ticker) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(ticker);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"GetFirst",args,com.intersys.objects.Database.RET_PRIM);
        return res.getInteger();
    }
    /**
     <p>Runs method GetLast in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param ticker represented as java.lang.Integer
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#GetLast"> Method GetLast</A>
    */
    public static java.lang.Integer GetLast (com.intersys.objects.Database db, java.lang.Integer ticker) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(ticker);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"GetLast",args,com.intersys.objects.Database.RET_PRIM);
        return res.getInteger();
    }
    /**
     <p>Runs method GetPoorLast in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param ticker represented as java.lang.Integer
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#GetPoorLast"> Method GetPoorLast</A>
    */
    public static java.lang.Integer GetPoorLast (com.intersys.objects.Database db, java.lang.Integer ticker) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(ticker);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"GetPoorLast",args,com.intersys.objects.Database.RET_PRIM);
        return res.getInteger();
    }
    /**
     <p>Runs method HighDisplayToLogical in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param _val represented as java.lang.String
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#HighDisplayToLogical"> Method HighDisplayToLogical</A>
    */
    public static java.lang.Double HighDisplayToLogical (com.intersys.objects.Database db, java.lang.String _val) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(_val);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"HighDisplayToLogical",args,com.intersys.objects.Database.RET_PRIM);
        return res.getDouble();
    }
    /**
     <p>Runs method HighIsValid in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param _val represented as java.lang.String
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#HighIsValid"> Method HighIsValid</A>
    */
    public static void HighIsValid (com.intersys.objects.Database db, java.lang.String _val) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(_val);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"HighIsValid",args,com.intersys.objects.Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     <p>Runs method HighLogicalToDisplay in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param _val represented as java.lang.Double
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#HighLogicalToDisplay"> Method HighLogicalToDisplay</A>
    */
    public static java.lang.String HighLogicalToDisplay (com.intersys.objects.Database db, java.lang.Double _val) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(_val);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"HighLogicalToDisplay",args,com.intersys.objects.Database.RET_PRIM);
        return res.getString();
    }
    /**
     <p>Runs method IDKEYDelete in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param K1 represented as java.lang.String
     default argument concurrency set to -1
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see #IDKEYDelete(com.intersys.objects.Database,java.lang.String,java.lang.Integer)
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#IDKEYDelete"> Method IDKEYDelete</A>
    */
    public static void IDKEYDelete (com.intersys.objects.Database db, java.lang.String K1) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(K1);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"IDKEYDelete",args,com.intersys.objects.Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     <p>Runs method IDKEYDelete in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param K1 represented as java.lang.String
     @param concurrency represented as java.lang.Integer
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#IDKEYDelete"> Method IDKEYDelete</A>
    */
    public static void IDKEYDelete (com.intersys.objects.Database db, java.lang.String K1, java.lang.Integer concurrency) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[2];
        args[0] = new com.intersys.cache.Dataholder(K1);
        args[1] = new com.intersys.cache.Dataholder(concurrency);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"IDKEYDelete",args,com.intersys.objects.Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     <p>Runs method IDKEYExists in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param K1 represented as java.lang.String
     default argument id set to ""
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see #IDKEYExists(com.intersys.objects.Database,java.lang.String,com.intersys.objects.StringHolder)
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#IDKEYExists"> Method IDKEYExists</A>
    */
    public static java.lang.Boolean IDKEYExists (com.intersys.objects.Database db, java.lang.String K1) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(K1);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"IDKEYExists",args,com.intersys.objects.Database.RET_PRIM);
        return res.getBoolean();
    }
    /**
     <p>Runs method IDKEYExists in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param K1 represented as java.lang.String
     @param id represented as com.intersys.objects.StringHolder
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#IDKEYExists"> Method IDKEYExists</A>
    */
    public static java.lang.Boolean IDKEYExists (com.intersys.objects.Database db, java.lang.String K1, com.intersys.objects.StringHolder id) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[2];
        int[] _refs = new int[1];
        args[0] = new com.intersys.cache.Dataholder(K1);
        args[1] = com.intersys.cache.Dataholder.create (id.value);
        _refs[0] = 2;
        com.intersys.cache.Dataholder[] res=db.runClassMethod(CACHE_CLASS_NAME,"IDKEYExists",_refs,args,com.intersys.objects.Database.RET_PRIM);
        id.set(res[1].getString());
        return res[0].getBoolean();
    }
    /**
     <p>Runs method IDKEYOpen in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param K1 represented as java.lang.String
     default argument concurrency set to -1
     default argument sc set to $$$OK
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see #IDKEYOpen(com.intersys.objects.Database,java.lang.String,java.lang.Integer,com.intersys.objects.StatusCodeHolder)
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#IDKEYOpen"> Method IDKEYOpen</A>
    */
    public static IO.InternalIO IDKEYOpen (com.intersys.objects.Database db, java.lang.String K1) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(K1);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"IDKEYOpen",args,com.intersys.objects.Database.RET_OBJECT);
        com.intersys.cache.CacheObject cobj = res.getCacheObject();
        if (cobj == null)
            return null;
        return (IO.InternalIO)(cobj.newJavaInstance());
    }
    /**
     <p>Runs method IDKEYOpen in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param K1 represented as java.lang.String
     @param concurrency represented as java.lang.Integer
     default argument sc set to $$$OK
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see #IDKEYOpen(com.intersys.objects.Database,java.lang.String,java.lang.Integer,com.intersys.objects.StatusCodeHolder)
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#IDKEYOpen"> Method IDKEYOpen</A>
    */
    public static IO.InternalIO IDKEYOpen (com.intersys.objects.Database db, java.lang.String K1, java.lang.Integer concurrency) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[2];
        args[0] = new com.intersys.cache.Dataholder(K1);
        args[1] = new com.intersys.cache.Dataholder(concurrency);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"IDKEYOpen",args,com.intersys.objects.Database.RET_OBJECT);
        com.intersys.cache.CacheObject cobj = res.getCacheObject();
        if (cobj == null)
            return null;
        return (IO.InternalIO)(cobj.newJavaInstance());
    }
    /**
     <p>Runs method IDKEYOpen in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param K1 represented as java.lang.String
     @param concurrency represented as java.lang.Integer
     @param sc represented as com.intersys.objects.StatusCodeHolder
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#IDKEYOpen"> Method IDKEYOpen</A>
    */
    public static IO.InternalIO IDKEYOpen (com.intersys.objects.Database db, java.lang.String K1, java.lang.Integer concurrency, com.intersys.objects.StatusCodeHolder sc) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[3];
        int[] _refs = new int[1];
        args[0] = new com.intersys.cache.Dataholder(K1);
        args[1] = new com.intersys.cache.Dataholder(concurrency);
        args[2] = com.intersys.cache.Dataholder.create (sc.value);
        _refs[0] = 3;
        com.intersys.cache.Dataholder[] res=db.runClassMethod(CACHE_CLASS_NAME,"IDKEYOpen",_refs,args,com.intersys.objects.Database.RET_OBJECT);
        sc.set(res[1].getStatusCode());
        com.intersys.cache.CacheObject cobj = res[0].getCacheObject();
        if (cobj == null)
            return null;
        return (IO.InternalIO)(cobj.newJavaInstance());
    }
    /**
     <p>Runs method LowDisplayToLogical in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param _val represented as java.lang.String
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#LowDisplayToLogical"> Method LowDisplayToLogical</A>
    */
    public static java.lang.Double LowDisplayToLogical (com.intersys.objects.Database db, java.lang.String _val) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(_val);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"LowDisplayToLogical",args,com.intersys.objects.Database.RET_PRIM);
        return res.getDouble();
    }
    /**
     <p>Runs method LowIsValid in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param _val represented as java.lang.String
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#LowIsValid"> Method LowIsValid</A>
    */
    public static void LowIsValid (com.intersys.objects.Database db, java.lang.String _val) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(_val);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"LowIsValid",args,com.intersys.objects.Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     <p>Runs method LowLogicalToDisplay in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param _val represented as java.lang.Double
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#LowLogicalToDisplay"> Method LowLogicalToDisplay</A>
    */
    public static java.lang.String LowLogicalToDisplay (com.intersys.objects.Database db, java.lang.Double _val) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(_val);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"LowLogicalToDisplay",args,com.intersys.objects.Database.RET_PRIM);
        return res.getString();
    }
    /**
     <p>Runs method OpenDisplayToLogical in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param _val represented as java.lang.String
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#OpenDisplayToLogical"> Method OpenDisplayToLogical</A>
    */
    public static java.lang.Double OpenDisplayToLogical (com.intersys.objects.Database db, java.lang.String _val) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(_val);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"OpenDisplayToLogical",args,com.intersys.objects.Database.RET_PRIM);
        return res.getDouble();
    }
    /**
     <p>Runs method OpenIsValid in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param _val represented as java.lang.String
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#OpenIsValid"> Method OpenIsValid</A>
    */
    public static void OpenIsValid (com.intersys.objects.Database db, java.lang.String _val) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(_val);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"OpenIsValid",args,com.intersys.objects.Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     <p>Runs method OpenLogicalToDisplay in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param _val represented as java.lang.Double
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#OpenLogicalToDisplay"> Method OpenLogicalToDisplay</A>
    */
    public static java.lang.String OpenLogicalToDisplay (com.intersys.objects.Database db, java.lang.Double _val) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(_val);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"OpenLogicalToDisplay",args,com.intersys.objects.Database.RET_PRIM);
        return res.getString();
    }
    /**
     <p>Runs method ParseFile in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param file represented as java.lang.String
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#ParseFile"> Method ParseFile</A>
    */
    public static void ParseFile (com.intersys.objects.Database db, java.lang.String file) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(file);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"ParseFile",args,com.intersys.objects.Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     <p>Runs method Populate in Cache.</p>
     <p>Description: Creates up to <var>count</var> instances an object and stores 
them in the database.

<p>If <var>verbose</var> is true, then details are echoed to the console.
<p>If <var>DeferIndices</var> is true, then indices are sorted at the end of the operation.
<p>If <var>objects</var> is true, then each object that is created is returned in the objects array
<p>If <var>tune</var> is true, then TuneTable^%apiSQL is called after the instances of the class have been created.

<P>Returns the number of instances successfully created.

<p>For more information refer to 
<LINK href=DocBook.UI.Page.cls?KEY=GOBJ_populate>The Cach&eacute; Populate Utility</LINK>.</p>
     @param db represented as com.intersys.objects.Database
     default argument count set to 10
     default argument verbose set to 0
     default argument DeferIndices set to 1
     default argument objects set to 0
     default argument tune set to 1
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see #Populate(com.intersys.objects.Database,java.lang.Integer,java.lang.Integer,java.lang.Integer,com.intersys.objects.IntegerHolder,java.lang.Integer)
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#Populate"> Method Populate</A>
    */
    public static java.lang.Integer Populate (com.intersys.objects.Database db) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[0];
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"Populate",args,com.intersys.objects.Database.RET_PRIM);
        return res.getInteger();
    }
    /**
     <p>Runs method Populate in Cache.</p>
     <p>Description: Creates up to <var>count</var> instances an object and stores 
them in the database.

<p>If <var>verbose</var> is true, then details are echoed to the console.
<p>If <var>DeferIndices</var> is true, then indices are sorted at the end of the operation.
<p>If <var>objects</var> is true, then each object that is created is returned in the objects array
<p>If <var>tune</var> is true, then TuneTable^%apiSQL is called after the instances of the class have been created.

<P>Returns the number of instances successfully created.

<p>For more information refer to 
<LINK href=DocBook.UI.Page.cls?KEY=GOBJ_populate>The Cach&eacute; Populate Utility</LINK>.</p>
     @param db represented as com.intersys.objects.Database
     @param count represented as java.lang.Integer
     default argument verbose set to 0
     default argument DeferIndices set to 1
     default argument objects set to 0
     default argument tune set to 1
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see #Populate(com.intersys.objects.Database,java.lang.Integer,java.lang.Integer,java.lang.Integer,com.intersys.objects.IntegerHolder,java.lang.Integer)
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#Populate"> Method Populate</A>
    */
    public static java.lang.Integer Populate (com.intersys.objects.Database db, java.lang.Integer count) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(count);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"Populate",args,com.intersys.objects.Database.RET_PRIM);
        return res.getInteger();
    }
    /**
     <p>Runs method Populate in Cache.</p>
     <p>Description: Creates up to <var>count</var> instances an object and stores 
them in the database.

<p>If <var>verbose</var> is true, then details are echoed to the console.
<p>If <var>DeferIndices</var> is true, then indices are sorted at the end of the operation.
<p>If <var>objects</var> is true, then each object that is created is returned in the objects array
<p>If <var>tune</var> is true, then TuneTable^%apiSQL is called after the instances of the class have been created.

<P>Returns the number of instances successfully created.

<p>For more information refer to 
<LINK href=DocBook.UI.Page.cls?KEY=GOBJ_populate>The Cach&eacute; Populate Utility</LINK>.</p>
     @param db represented as com.intersys.objects.Database
     @param count represented as java.lang.Integer
     @param verbose represented as java.lang.Integer
     default argument DeferIndices set to 1
     default argument objects set to 0
     default argument tune set to 1
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see #Populate(com.intersys.objects.Database,java.lang.Integer,java.lang.Integer,java.lang.Integer,com.intersys.objects.IntegerHolder,java.lang.Integer)
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#Populate"> Method Populate</A>
    */
    public static java.lang.Integer Populate (com.intersys.objects.Database db, java.lang.Integer count, java.lang.Integer verbose) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[2];
        args[0] = new com.intersys.cache.Dataholder(count);
        args[1] = new com.intersys.cache.Dataholder(verbose);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"Populate",args,com.intersys.objects.Database.RET_PRIM);
        return res.getInteger();
    }
    /**
     <p>Runs method Populate in Cache.</p>
     <p>Description: Creates up to <var>count</var> instances an object and stores 
them in the database.

<p>If <var>verbose</var> is true, then details are echoed to the console.
<p>If <var>DeferIndices</var> is true, then indices are sorted at the end of the operation.
<p>If <var>objects</var> is true, then each object that is created is returned in the objects array
<p>If <var>tune</var> is true, then TuneTable^%apiSQL is called after the instances of the class have been created.

<P>Returns the number of instances successfully created.

<p>For more information refer to 
<LINK href=DocBook.UI.Page.cls?KEY=GOBJ_populate>The Cach&eacute; Populate Utility</LINK>.</p>
     @param db represented as com.intersys.objects.Database
     @param count represented as java.lang.Integer
     @param verbose represented as java.lang.Integer
     @param DeferIndices represented as java.lang.Integer
     default argument objects set to 0
     default argument tune set to 1
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see #Populate(com.intersys.objects.Database,java.lang.Integer,java.lang.Integer,java.lang.Integer,com.intersys.objects.IntegerHolder,java.lang.Integer)
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#Populate"> Method Populate</A>
    */
    public static java.lang.Integer Populate (com.intersys.objects.Database db, java.lang.Integer count, java.lang.Integer verbose, java.lang.Integer DeferIndices) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[3];
        args[0] = new com.intersys.cache.Dataholder(count);
        args[1] = new com.intersys.cache.Dataholder(verbose);
        args[2] = new com.intersys.cache.Dataholder(DeferIndices);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"Populate",args,com.intersys.objects.Database.RET_PRIM);
        return res.getInteger();
    }
    /**
     <p>Runs method Populate in Cache.</p>
     <p>Description: Creates up to <var>count</var> instances an object and stores 
them in the database.

<p>If <var>verbose</var> is true, then details are echoed to the console.
<p>If <var>DeferIndices</var> is true, then indices are sorted at the end of the operation.
<p>If <var>objects</var> is true, then each object that is created is returned in the objects array
<p>If <var>tune</var> is true, then TuneTable^%apiSQL is called after the instances of the class have been created.

<P>Returns the number of instances successfully created.

<p>For more information refer to 
<LINK href=DocBook.UI.Page.cls?KEY=GOBJ_populate>The Cach&eacute; Populate Utility</LINK>.</p>
     @param db represented as com.intersys.objects.Database
     @param count represented as java.lang.Integer
     @param verbose represented as java.lang.Integer
     @param DeferIndices represented as java.lang.Integer
     @param objects represented as com.intersys.objects.IntegerHolder
     default argument tune set to 1
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see #Populate(com.intersys.objects.Database,java.lang.Integer,java.lang.Integer,java.lang.Integer,com.intersys.objects.IntegerHolder,java.lang.Integer)
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#Populate"> Method Populate</A>
    */
    public static java.lang.Integer Populate (com.intersys.objects.Database db, java.lang.Integer count, java.lang.Integer verbose, java.lang.Integer DeferIndices, com.intersys.objects.IntegerHolder objects) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[4];
        int[] _refs = new int[1];
        args[0] = new com.intersys.cache.Dataholder(count);
        args[1] = new com.intersys.cache.Dataholder(verbose);
        args[2] = new com.intersys.cache.Dataholder(DeferIndices);
        args[3] = com.intersys.cache.Dataholder.create (objects.value);
        _refs[0] = 4;
        com.intersys.cache.Dataholder[] res=db.runClassMethod(CACHE_CLASS_NAME,"Populate",_refs,args,com.intersys.objects.Database.RET_PRIM);
        objects.set(res[1].getInteger());
        return res[0].getInteger();
    }
    /**
     <p>Runs method Populate in Cache.</p>
     <p>Description: Creates up to <var>count</var> instances an object and stores 
them in the database.

<p>If <var>verbose</var> is true, then details are echoed to the console.
<p>If <var>DeferIndices</var> is true, then indices are sorted at the end of the operation.
<p>If <var>objects</var> is true, then each object that is created is returned in the objects array
<p>If <var>tune</var> is true, then TuneTable^%apiSQL is called after the instances of the class have been created.

<P>Returns the number of instances successfully created.

<p>For more information refer to 
<LINK href=DocBook.UI.Page.cls?KEY=GOBJ_populate>The Cach&eacute; Populate Utility</LINK>.</p>
     @param db represented as com.intersys.objects.Database
     @param count represented as java.lang.Integer
     @param verbose represented as java.lang.Integer
     @param DeferIndices represented as java.lang.Integer
     @param objects represented as com.intersys.objects.IntegerHolder
     @param tune represented as java.lang.Integer
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#Populate"> Method Populate</A>
    */
    public static java.lang.Integer Populate (com.intersys.objects.Database db, java.lang.Integer count, java.lang.Integer verbose, java.lang.Integer DeferIndices, com.intersys.objects.IntegerHolder objects, java.lang.Integer tune) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[5];
        int[] _refs = new int[1];
        args[0] = new com.intersys.cache.Dataholder(count);
        args[1] = new com.intersys.cache.Dataholder(verbose);
        args[2] = new com.intersys.cache.Dataholder(DeferIndices);
        args[3] = com.intersys.cache.Dataholder.create (objects.value);
        _refs[0] = 4;
        args[4] = new com.intersys.cache.Dataholder(tune);
        com.intersys.cache.Dataholder[] res=db.runClassMethod(CACHE_CLASS_NAME,"Populate",_refs,args,com.intersys.objects.Database.RET_PRIM);
        objects.set(res[1].getInteger());
        return res[0].getInteger();
    }
    /**
     <p>Runs method PopulateSerial in Cache.</p>
     <p>Description: Create a single instance of a serial object.</p>
     @param db represented as com.intersys.objects.Database
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#PopulateSerial"> Method PopulateSerial</A>
    */
    public static java.lang.String PopulateSerial (com.intersys.objects.Database db) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[0];
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"PopulateSerial",args,com.intersys.objects.Database.RET_PRIM);
        return res.getString();
    }
    /**
     <p>Runs method TickerGetObject in Cache.</p>
     default argument force set to 0
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see #TickerGetObject(java.lang.Integer)
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#TickerGetObject"> Method TickerGetObject</A>
    */
    public com.intersys.objects.Oid TickerGetObject () throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[0];
        com.intersys.cache.Dataholder res=mInternal.runInstanceMethod("TickerGetObject",args,com.intersys.objects.Database.RET_PRIM);
        return res.getOid();
    }
    /**
     <p>Runs method TickerGetObject in Cache.</p>
     @param force represented as java.lang.Integer
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#TickerGetObject"> Method TickerGetObject</A>
    */
    public com.intersys.objects.Oid TickerGetObject (java.lang.Integer force) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(force);
        com.intersys.cache.Dataholder res=mInternal.runInstanceMethod("TickerGetObject",args,com.intersys.objects.Database.RET_PRIM);
        return res.getOid();
    }
    /**
     <p>Runs method TickerGetObjectId in Cache.</p>
     default argument force set to 0
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see #TickerGetObjectId(java.lang.Integer)
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#TickerGetObjectId"> Method TickerGetObjectId</A>
    */
    public java.lang.String TickerGetObjectId () throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[0];
        com.intersys.cache.Dataholder res=mInternal.runInstanceMethod("TickerGetObjectId",args,com.intersys.objects.Database.RET_PRIM);
        return res.getString();
    }
    /**
     <p>Runs method TickerGetObjectId in Cache.</p>
     @param force represented as java.lang.Integer
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#TickerGetObjectId"> Method TickerGetObjectId</A>
    */
    public java.lang.String TickerGetObjectId (java.lang.Integer force) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(force);
        com.intersys.cache.Dataholder res=mInternal.runInstanceMethod("TickerGetObjectId",args,com.intersys.objects.Database.RET_PRIM);
        return res.getString();
    }
    /**
     <p>Runs method TickerIndexExists in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param K1 represented as Resources.Tickers
     default argument id set to ""
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see #TickerIndexExists(com.intersys.objects.Database,Resources.Tickers,com.intersys.objects.StringHolder)
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#TickerIndexExists"> Method TickerIndexExists</A>
    */
    public static java.lang.Boolean TickerIndexExists (com.intersys.objects.Database db, Resources.Tickers K1) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(K1);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"TickerIndexExists",args,com.intersys.objects.Database.RET_PRIM);
        return res.getBoolean();
    }
    /**
     <p>Runs method TickerIndexExists in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param K1 represented as Resources.Tickers
     @param id represented as com.intersys.objects.StringHolder
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#TickerIndexExists"> Method TickerIndexExists</A>
    */
    public static java.lang.Boolean TickerIndexExists (com.intersys.objects.Database db, Resources.Tickers K1, com.intersys.objects.StringHolder id) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[2];
        int[] _refs = new int[1];
        args[0] = new com.intersys.cache.Dataholder(K1);
        args[1] = com.intersys.cache.Dataholder.create (id.value);
        _refs[0] = 2;
        com.intersys.cache.Dataholder[] res=db.runClassMethod(CACHE_CLASS_NAME,"TickerIndexExists",_refs,args,com.intersys.objects.Database.RET_PRIM);
        id.set(res[1].getString());
        return res[0].getBoolean();
    }
    /**
     <p>Runs method TickerIsValid in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param value represented as java.lang.String
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#TickerIsValid"> Method TickerIsValid</A>
    */
    public static void TickerIsValid (com.intersys.objects.Database db, java.lang.String value) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(value);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"TickerIsValid",args,com.intersys.objects.Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     <p>Runs method TickerSetObject in Cache.</p>
     @param newvalue represented as com.intersys.objects.Oid
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#TickerSetObject"> Method TickerSetObject</A>
    */
    public void TickerSetObject (com.intersys.objects.Oid newvalue) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(newvalue);
        com.intersys.cache.Dataholder res=mInternal.runInstanceMethod("TickerSetObject",args,com.intersys.objects.Database.RET_PRIM);
        getDatabase().parseStatus(res);
        return;
    }
    /**
     <p>Runs method TickerSetObjectId in Cache.</p>
     @param newid represented as java.lang.String
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#TickerSetObjectId"> Method TickerSetObjectId</A>
    */
    public void TickerSetObjectId (java.lang.String newid) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(newid);
        com.intersys.cache.Dataholder res=mInternal.runInstanceMethod("TickerSetObjectId",args,com.intersys.objects.Database.RET_PRIM);
        getDatabase().parseStatus(res);
        return;
    }
    /**
     <p>Runs method VolumeDisplayToLogical in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param _val represented as java.lang.String
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#VolumeDisplayToLogical"> Method VolumeDisplayToLogical</A>
    */
    public static java.lang.Double VolumeDisplayToLogical (com.intersys.objects.Database db, java.lang.String _val) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(_val);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"VolumeDisplayToLogical",args,com.intersys.objects.Database.RET_PRIM);
        return res.getDouble();
    }
    /**
     <p>Runs method VolumeIsValid in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param _val represented as java.lang.String
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#VolumeIsValid"> Method VolumeIsValid</A>
    */
    public static void VolumeIsValid (com.intersys.objects.Database db, java.lang.String _val) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(_val);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"VolumeIsValid",args,com.intersys.objects.Database.RET_PRIM);
        db.parseStatus(res);
        return;
    }
    /**
     <p>Runs method VolumeLogicalToDisplay in Cache.</p>
     @param db represented as com.intersys.objects.Database
     @param _val represented as java.lang.Double
     @throws com.intersys.objects.CacheException if any error occured while running the method.
     @see <a href = "http://cache-server:57772/csp/documatic/%25CSP.Documatic.cls?APP=1&PAGE=CLASS&LIBRARY=LISE_F&CLASSNAME=IO.InternalIO#VolumeLogicalToDisplay"> Method VolumeLogicalToDisplay</A>
    */
    public static java.lang.String VolumeLogicalToDisplay (com.intersys.objects.Database db, java.lang.Double _val) throws com.intersys.objects.CacheException {
        com.intersys.cache.Dataholder[] args = new com.intersys.cache.Dataholder[1];
        args[0] = new com.intersys.cache.Dataholder(_val);
        com.intersys.cache.Dataholder res=db.runClassMethod(CACHE_CLASS_NAME,"VolumeLogicalToDisplay",args,com.intersys.objects.Database.RET_PRIM);
        return res.getString();
    }
    /**
     <p>Returns a CallableStatement for query Extent.</p>
     @param db represented as com.intersys.objects.Database
     @throws com.intersys.objects.CacheException if any error occured while running the method.
    */
    public static com.intersys.objects.CacheQuery query_Extent (com.intersys.objects.Database db) throws com.intersys.objects.CacheException {
        return new com.intersys.objects.CacheQuery(db, "IO.InternalIO_Extent", 0, 0);
    }

    public static Object addToBatchInsert (Object batch, java.sql.Connection con, Double cos_Close, java.sql.Date cos_CreatedDate, java.sql.Time cos_CreatedTime, Double cos_High, Double cos_Low, Double cos_Open, Integer cos_Ticker, Double cos_Volume) throws java.sql.SQLException {
        if (batch == null) {
            com.intersys.jdbc.CacheConnection c = null;
            if (con != null) {
                try {
                    c = com.intersys.cache.jdbcutil.JDBCAdapter.getCacheConnection (con);
                } catch (com.intersys.objects.CacheException x) {
                    throw new java.sql.SQLException ("Connection is not a CacheConnection.");
                }
            }
            batch = new com.intersys.jdbc.QuickStatement.Batch (c.getConnectionInfo ());
        }
        com.intersys.jdbc.QuickStatement.Batch qbatch = (com.intersys.jdbc.QuickStatement.Batch) batch;
        com.intersys.jdbc.SysListProxy.setInteger (qbatch.list, 9); // number of columns
        com.intersys.jdbc.SysListProxy.setDoubleWrapper(qbatch.list, cos_Close);
        com.intersys.jdbc.SysListProxy.setSQLDate(qbatch.list, cos_CreatedDate);
        com.intersys.jdbc.SysListProxy.setSQLTime(qbatch.list, cos_CreatedTime);
        com.intersys.jdbc.SysListProxy.setDoubleWrapper(qbatch.list, cos_High);
        com.intersys.jdbc.SysListProxy.setDoubleWrapper(qbatch.list, cos_Low);
        com.intersys.jdbc.SysListProxy.setDoubleWrapper(qbatch.list, cos_Open);
        com.intersys.jdbc.SysListProxy.setIntegerWrapper(qbatch.list, cos_Ticker);
        com.intersys.jdbc.SysListProxy.setDoubleWrapper(qbatch.list, cos_Volume);
        com.intersys.jdbc.SysListProxy.setUndefined(qbatch.list); // for x__classname
        qbatch.flushRecord ();
        return qbatch;
    }
    
    public static java.util.List executeBatchInsert (java.sql.Connection con, Object batch, int nolock) throws java.sql.SQLException {
        Object ids = com.intersys.jdbc.QuickStatement.Batch.execute ("IO", "InternalIO", 4, batch, con, nolock);
        return new com.intersys.objects.SList (ids);
        }
}
