/**
 * This class is generated by jOOQ
 */
package br.unb.sma.database.tables;


import br.unb.sma.database.Keys;
import br.unb.sma.database.Procjud;
import br.unb.sma.database.tables.records.TProcessoParteRecord;
import org.jooq.*;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.List;


/**
 * Relaciona as Partes interessadas em Processos Judiciais
 */
@Generated(
		value = {
				"http://www.jooq.org",
				"jOOQ version:3.7.3"
		},
		comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class TProcessoParte extends TableImpl<TProcessoParteRecord> {

	/**
	 * The reference instance of <code>procjud.t_processo_parte</code>
	 */
	public static final TProcessoParte T_PROCESSO_PARTE = new TProcessoParte();
	private static final long serialVersionUID = -653820427;
	/**
	 * The column <code>procjud.t_processo_parte.cod_processo</code>. Codigo numerico atribuido ao processo para identifica-lo no banco de dados
	 */
	public final TableField<TProcessoParteRecord, Long> COD_PROCESSO = createField("cod_processo", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "Codigo numerico atribuido ao processo para identifica-lo no banco de dados");
	/**
	 * The column <code>procjud.t_processo_parte.cod_parte</code>. Codigo identificador da parte
	 */
	public final TableField<TProcessoParteRecord, Long> COD_PARTE = createField("cod_parte", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "Codigo identificador da parte");

	/**
	 * Create a <code>procjud.t_processo_parte</code> table reference
	 */
	public TProcessoParte() {
		this("t_processo_parte", null);
	}

	/**
	 * Create an aliased <code>procjud.t_processo_parte</code> table reference
	 */
	public TProcessoParte(String alias) {
		this(alias, T_PROCESSO_PARTE);
	}

	private TProcessoParte(String alias, Table<TProcessoParteRecord> aliased) {
		this(alias, aliased, null);
	}

	private TProcessoParte(String alias, Table<TProcessoParteRecord> aliased, Field<?>[] parameters) {
		super(alias, Procjud.PROCJUD, aliased, parameters, "Relaciona as Partes interessadas em Processos Judiciais");
	}

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<TProcessoParteRecord> getRecordType() {
		return TProcessoParteRecord.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<TProcessoParteRecord> getPrimaryKey() {
		return Keys.PROCESSO_PARTE_PK;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<TProcessoParteRecord>> getKeys() {
		return Arrays.<UniqueKey<TProcessoParteRecord>>asList(Keys.PROCESSO_PARTE_PK);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<TProcessoParteRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<TProcessoParteRecord, ?>>asList(Keys.T_PROCESSO_PARTE__PROCESSO_PARTE_FK1, Keys.T_PROCESSO_PARTE__PROCESSO_PARTE_FK2);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TProcessoParte as(String alias) {
		return new TProcessoParte(alias, this);
	}

	/**
	 * Rename this table
	 */
	public TProcessoParte rename(String name) {
		return new TProcessoParte(name, null);
	}
}
