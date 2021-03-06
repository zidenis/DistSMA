/**
 * This class is generated by jOOQ
 */
package br.unb.sma.database.tables;


import br.unb.sma.database.Keys;
import br.unb.sma.database.Procjud;
import br.unb.sma.database.tables.records.TProcessoParteAdvogadoRecord;
import org.jooq.*;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.List;


/**
 * Relaciona os Advogados das Partes interessadas em Processos Judiciais
 */
@Generated(
		value = {
				"http://www.jooq.org",
				"jOOQ version:3.7.3"
		},
		comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class TProcessoParteAdvogado extends TableImpl<TProcessoParteAdvogadoRecord> {

	/**
	 * The reference instance of <code>procjud.t_processo_parte_advogado</code>
	 */
	public static final TProcessoParteAdvogado T_PROCESSO_PARTE_ADVOGADO = new TProcessoParteAdvogado();
	private static final long serialVersionUID = -45849808;
	/**
	 * The column <code>procjud.t_processo_parte_advogado.cod_processo</code>. Codigo numerico atribuido ao processo para identifica-lo no banco de dados
	 */
	public final TableField<TProcessoParteAdvogadoRecord, Long> COD_PROCESSO = createField("cod_processo", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "Codigo numerico atribuido ao processo para identifica-lo no banco de dados");
	/**
	 * The column <code>procjud.t_processo_parte_advogado.cod_parte</code>. Codigo identificador da parte
	 */
	public final TableField<TProcessoParteAdvogadoRecord, Long> COD_PARTE = createField("cod_parte", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "Codigo identificador da parte");
	/**
	 * The column <code>procjud.t_processo_parte_advogado.num_advogado</code>. Numero de identificacao do Advogado
	 */
	public final TableField<TProcessoParteAdvogadoRecord, Integer> NUM_ADVOGADO = createField("num_advogado", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "Numero de identificacao do Advogado");

	/**
	 * Create a <code>procjud.t_processo_parte_advogado</code> table reference
	 */
	public TProcessoParteAdvogado() {
		this("t_processo_parte_advogado", null);
	}

	/**
	 * Create an aliased <code>procjud.t_processo_parte_advogado</code> table reference
	 */
	public TProcessoParteAdvogado(String alias) {
		this(alias, T_PROCESSO_PARTE_ADVOGADO);
	}

	private TProcessoParteAdvogado(String alias, Table<TProcessoParteAdvogadoRecord> aliased) {
		this(alias, aliased, null);
	}

	private TProcessoParteAdvogado(String alias, Table<TProcessoParteAdvogadoRecord> aliased, Field<?>[] parameters) {
		super(alias, Procjud.PROCJUD, aliased, parameters, "Relaciona os Advogados das Partes interessadas em Processos Judiciais");
	}

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<TProcessoParteAdvogadoRecord> getRecordType() {
		return TProcessoParteAdvogadoRecord.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<TProcessoParteAdvogadoRecord> getPrimaryKey() {
		return Keys.PROCESSO_PARTE_ADVOGADO_PK;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<TProcessoParteAdvogadoRecord>> getKeys() {
		return Arrays.<UniqueKey<TProcessoParteAdvogadoRecord>>asList(Keys.PROCESSO_PARTE_ADVOGADO_PK);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<TProcessoParteAdvogadoRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<TProcessoParteAdvogadoRecord, ?>>asList(Keys.T_PROCESSO_PARTE_ADVOGADO__PROCESSO_PARTE_ADVOGADO_FK1, Keys.T_PROCESSO_PARTE_ADVOGADO__PROCESSO_PARTE_ADVOGADO_FK2);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TProcessoParteAdvogado as(String alias) {
		return new TProcessoParteAdvogado(alias, this);
	}

	/**
	 * Rename this table
	 */
	public TProcessoParteAdvogado rename(String name) {
		return new TProcessoParteAdvogado(name, null);
	}
}
