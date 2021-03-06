/**
 * This class is generated by jOOQ
 */
package br.unb.sma.database.tables;


import br.unb.sma.database.Keys;
import br.unb.sma.database.Procjud;
import br.unb.sma.database.tables.records.TProtocoloRecord;
import org.jooq.Field;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.List;


/**
 * Unidades de protocolo
 */
@Generated(
		value = {
				"http://www.jooq.org",
				"jOOQ version:3.7.3"
		},
		comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class TProtocolo extends TableImpl<TProtocoloRecord> {

	/**
	 * The reference instance of <code>procjud.t_protocolo</code>
	 */
	public static final TProtocolo T_PROTOCOLO = new TProtocolo();
	private static final long serialVersionUID = 120261571;
	/**
	 * The column <code>procjud.t_protocolo.cod_protocolo</code>. Código atribuído a unidade de protocolo
	 */
	public final TableField<TProtocoloRecord, String> COD_PROTOCOLO = createField("cod_protocolo", org.jooq.impl.SQLDataType.VARCHAR.length(8).nullable(false), this, "Código atribuído a unidade de protocolo");
	/**
	 * The column <code>procjud.t_protocolo.nom_protocolo</code>. Nome dado a unidade de protocolo
	 */
	public final TableField<TProtocoloRecord, String> NOM_PROTOCOLO = createField("nom_protocolo", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "Nome dado a unidade de protocolo");
	/**
	 * The column <code>procjud.t_protocolo.num_tribunal</code>. Número do Tribunal referente ao protocolo
	 */
	public final TableField<TProtocoloRecord, Byte> NUM_TRIBUNAL = createField("num_tribunal", org.jooq.impl.SQLDataType.TINYINT.nullable(false), this, "Número do Tribunal referente ao protocolo");

	/**
	 * Create a <code>procjud.t_protocolo</code> table reference
	 */
	public TProtocolo() {
		this("t_protocolo", null);
	}

	/**
	 * Create an aliased <code>procjud.t_protocolo</code> table reference
	 */
	public TProtocolo(String alias) {
		this(alias, T_PROTOCOLO);
	}

	private TProtocolo(String alias, Table<TProtocoloRecord> aliased) {
		this(alias, aliased, null);
	}

	private TProtocolo(String alias, Table<TProtocoloRecord> aliased, Field<?>[] parameters) {
		super(alias, Procjud.PROCJUD, aliased, parameters, "Unidades de protocolo");
	}

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<TProtocoloRecord> getRecordType() {
		return TProtocoloRecord.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<TProtocoloRecord> getPrimaryKey() {
		return Keys.PK_T_PROTOCOLO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<TProtocoloRecord>> getKeys() {
		return Arrays.<UniqueKey<TProtocoloRecord>>asList(Keys.PK_T_PROTOCOLO);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TProtocolo as(String alias) {
		return new TProtocolo(alias, this);
	}

	/**
	 * Rename this table
	 */
	public TProtocolo rename(String name) {
		return new TProtocolo(name, null);
	}
}
