/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.github;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class Payload extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -5393701868636233885L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Payload\",\"namespace\":\"com.github\",\"fields\":[{\"name\":\"push_id\",\"type\":\"long\"},{\"name\":\"size\",\"type\":\"int\"},{\"name\":\"distinct_size\",\"type\":\"int\"},{\"name\":\"ref\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"head\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"before\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"commits\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"PushCommit\",\"fields\":[{\"name\":\"sha\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"author\",\"type\":{\"type\":\"record\",\"name\":\"PushAuthor\",\"fields\":[{\"name\":\"email\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"name\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}},{\"name\":\"message\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"distinct\",\"type\":\"boolean\"}]}}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<Payload> ENCODER =
      new BinaryMessageEncoder<Payload>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<Payload> DECODER =
      new BinaryMessageDecoder<Payload>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<Payload> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<Payload> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<Payload>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this Payload to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a Payload from a ByteBuffer. */
  public static Payload fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

   private long push_id;
   private int size;
   private int distinct_size;
   private java.lang.String ref;
   private java.lang.String head;
   private java.lang.String before;
   private java.util.List<com.github.PushCommit> commits;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public Payload() {}

  /**
   * All-args constructor.
   * @param push_id The new value for push_id
   * @param size The new value for size
   * @param distinct_size The new value for distinct_size
   * @param ref The new value for ref
   * @param head The new value for head
   * @param before The new value for before
   * @param commits The new value for commits
   */
  public Payload(java.lang.Long push_id, java.lang.Integer size, java.lang.Integer distinct_size, java.lang.String ref, java.lang.String head, java.lang.String before, java.util.List<com.github.PushCommit> commits) {
    this.push_id = push_id;
    this.size = size;
    this.distinct_size = distinct_size;
    this.ref = ref;
    this.head = head;
    this.before = before;
    this.commits = commits;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return push_id;
    case 1: return size;
    case 2: return distinct_size;
    case 3: return ref;
    case 4: return head;
    case 5: return before;
    case 6: return commits;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: push_id = (java.lang.Long)value$; break;
    case 1: size = (java.lang.Integer)value$; break;
    case 2: distinct_size = (java.lang.Integer)value$; break;
    case 3: ref = (java.lang.String)value$; break;
    case 4: head = (java.lang.String)value$; break;
    case 5: before = (java.lang.String)value$; break;
    case 6: commits = (java.util.List<com.github.PushCommit>)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'push_id' field.
   * @return The value of the 'push_id' field.
   */
  public java.lang.Long getPushId() {
    return push_id;
  }


  /**
   * Gets the value of the 'size' field.
   * @return The value of the 'size' field.
   */
  public java.lang.Integer getSize() {
    return size;
  }


  /**
   * Gets the value of the 'distinct_size' field.
   * @return The value of the 'distinct_size' field.
   */
  public java.lang.Integer getDistinctSize() {
    return distinct_size;
  }


  /**
   * Gets the value of the 'ref' field.
   * @return The value of the 'ref' field.
   */
  public java.lang.String getRef() {
    return ref;
  }


  /**
   * Gets the value of the 'head' field.
   * @return The value of the 'head' field.
   */
  public java.lang.String getHead() {
    return head;
  }


  /**
   * Gets the value of the 'before' field.
   * @return The value of the 'before' field.
   */
  public java.lang.String getBefore() {
    return before;
  }


  /**
   * Gets the value of the 'commits' field.
   * @return The value of the 'commits' field.
   */
  public java.util.List<com.github.PushCommit> getCommits() {
    return commits;
  }


  /**
   * Creates a new Payload RecordBuilder.
   * @return A new Payload RecordBuilder
   */
  public static com.github.Payload.Builder newBuilder() {
    return new com.github.Payload.Builder();
  }

  /**
   * Creates a new Payload RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new Payload RecordBuilder
   */
  public static com.github.Payload.Builder newBuilder(com.github.Payload.Builder other) {
    return new com.github.Payload.Builder(other);
  }

  /**
   * Creates a new Payload RecordBuilder by copying an existing Payload instance.
   * @param other The existing instance to copy.
   * @return A new Payload RecordBuilder
   */
  public static com.github.Payload.Builder newBuilder(com.github.Payload other) {
    return new com.github.Payload.Builder(other);
  }

  /**
   * RecordBuilder for Payload instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Payload>
    implements org.apache.avro.data.RecordBuilder<Payload> {

    private long push_id;
    private int size;
    private int distinct_size;
    private java.lang.String ref;
    private java.lang.String head;
    private java.lang.String before;
    private java.util.List<com.github.PushCommit> commits;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.github.Payload.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.push_id)) {
        this.push_id = data().deepCopy(fields()[0].schema(), other.push_id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.size)) {
        this.size = data().deepCopy(fields()[1].schema(), other.size);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.distinct_size)) {
        this.distinct_size = data().deepCopy(fields()[2].schema(), other.distinct_size);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.ref)) {
        this.ref = data().deepCopy(fields()[3].schema(), other.ref);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.head)) {
        this.head = data().deepCopy(fields()[4].schema(), other.head);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.before)) {
        this.before = data().deepCopy(fields()[5].schema(), other.before);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.commits)) {
        this.commits = data().deepCopy(fields()[6].schema(), other.commits);
        fieldSetFlags()[6] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing Payload instance
     * @param other The existing instance to copy.
     */
    private Builder(com.github.Payload other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.push_id)) {
        this.push_id = data().deepCopy(fields()[0].schema(), other.push_id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.size)) {
        this.size = data().deepCopy(fields()[1].schema(), other.size);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.distinct_size)) {
        this.distinct_size = data().deepCopy(fields()[2].schema(), other.distinct_size);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.ref)) {
        this.ref = data().deepCopy(fields()[3].schema(), other.ref);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.head)) {
        this.head = data().deepCopy(fields()[4].schema(), other.head);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.before)) {
        this.before = data().deepCopy(fields()[5].schema(), other.before);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.commits)) {
        this.commits = data().deepCopy(fields()[6].schema(), other.commits);
        fieldSetFlags()[6] = true;
      }
    }

    /**
      * Gets the value of the 'push_id' field.
      * @return The value.
      */
    public java.lang.Long getPushId() {
      return push_id;
    }

    /**
      * Sets the value of the 'push_id' field.
      * @param value The value of 'push_id'.
      * @return This builder.
      */
    public com.github.Payload.Builder setPushId(long value) {
      validate(fields()[0], value);
      this.push_id = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'push_id' field has been set.
      * @return True if the 'push_id' field has been set, false otherwise.
      */
    public boolean hasPushId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'push_id' field.
      * @return This builder.
      */
    public com.github.Payload.Builder clearPushId() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'size' field.
      * @return The value.
      */
    public java.lang.Integer getSize() {
      return size;
    }

    /**
      * Sets the value of the 'size' field.
      * @param value The value of 'size'.
      * @return This builder.
      */
    public com.github.Payload.Builder setSize(int value) {
      validate(fields()[1], value);
      this.size = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'size' field has been set.
      * @return True if the 'size' field has been set, false otherwise.
      */
    public boolean hasSize() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'size' field.
      * @return This builder.
      */
    public com.github.Payload.Builder clearSize() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'distinct_size' field.
      * @return The value.
      */
    public java.lang.Integer getDistinctSize() {
      return distinct_size;
    }

    /**
      * Sets the value of the 'distinct_size' field.
      * @param value The value of 'distinct_size'.
      * @return This builder.
      */
    public com.github.Payload.Builder setDistinctSize(int value) {
      validate(fields()[2], value);
      this.distinct_size = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'distinct_size' field has been set.
      * @return True if the 'distinct_size' field has been set, false otherwise.
      */
    public boolean hasDistinctSize() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'distinct_size' field.
      * @return This builder.
      */
    public com.github.Payload.Builder clearDistinctSize() {
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'ref' field.
      * @return The value.
      */
    public java.lang.String getRef() {
      return ref;
    }

    /**
      * Sets the value of the 'ref' field.
      * @param value The value of 'ref'.
      * @return This builder.
      */
    public com.github.Payload.Builder setRef(java.lang.String value) {
      validate(fields()[3], value);
      this.ref = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'ref' field has been set.
      * @return True if the 'ref' field has been set, false otherwise.
      */
    public boolean hasRef() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'ref' field.
      * @return This builder.
      */
    public com.github.Payload.Builder clearRef() {
      ref = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'head' field.
      * @return The value.
      */
    public java.lang.String getHead() {
      return head;
    }

    /**
      * Sets the value of the 'head' field.
      * @param value The value of 'head'.
      * @return This builder.
      */
    public com.github.Payload.Builder setHead(java.lang.String value) {
      validate(fields()[4], value);
      this.head = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'head' field has been set.
      * @return True if the 'head' field has been set, false otherwise.
      */
    public boolean hasHead() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'head' field.
      * @return This builder.
      */
    public com.github.Payload.Builder clearHead() {
      head = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'before' field.
      * @return The value.
      */
    public java.lang.String getBefore() {
      return before;
    }

    /**
      * Sets the value of the 'before' field.
      * @param value The value of 'before'.
      * @return This builder.
      */
    public com.github.Payload.Builder setBefore(java.lang.String value) {
      validate(fields()[5], value);
      this.before = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'before' field has been set.
      * @return True if the 'before' field has been set, false otherwise.
      */
    public boolean hasBefore() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'before' field.
      * @return This builder.
      */
    public com.github.Payload.Builder clearBefore() {
      before = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    /**
      * Gets the value of the 'commits' field.
      * @return The value.
      */
    public java.util.List<com.github.PushCommit> getCommits() {
      return commits;
    }

    /**
      * Sets the value of the 'commits' field.
      * @param value The value of 'commits'.
      * @return This builder.
      */
    public com.github.Payload.Builder setCommits(java.util.List<com.github.PushCommit> value) {
      validate(fields()[6], value);
      this.commits = value;
      fieldSetFlags()[6] = true;
      return this;
    }

    /**
      * Checks whether the 'commits' field has been set.
      * @return True if the 'commits' field has been set, false otherwise.
      */
    public boolean hasCommits() {
      return fieldSetFlags()[6];
    }


    /**
      * Clears the value of the 'commits' field.
      * @return This builder.
      */
    public com.github.Payload.Builder clearCommits() {
      commits = null;
      fieldSetFlags()[6] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Payload build() {
      try {
        Payload record = new Payload();
        record.push_id = fieldSetFlags()[0] ? this.push_id : (java.lang.Long) defaultValue(fields()[0]);
        record.size = fieldSetFlags()[1] ? this.size : (java.lang.Integer) defaultValue(fields()[1]);
        record.distinct_size = fieldSetFlags()[2] ? this.distinct_size : (java.lang.Integer) defaultValue(fields()[2]);
        record.ref = fieldSetFlags()[3] ? this.ref : (java.lang.String) defaultValue(fields()[3]);
        record.head = fieldSetFlags()[4] ? this.head : (java.lang.String) defaultValue(fields()[4]);
        record.before = fieldSetFlags()[5] ? this.before : (java.lang.String) defaultValue(fields()[5]);
        record.commits = fieldSetFlags()[6] ? this.commits : (java.util.List<com.github.PushCommit>) defaultValue(fields()[6]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<Payload>
    WRITER$ = (org.apache.avro.io.DatumWriter<Payload>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<Payload>
    READER$ = (org.apache.avro.io.DatumReader<Payload>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
