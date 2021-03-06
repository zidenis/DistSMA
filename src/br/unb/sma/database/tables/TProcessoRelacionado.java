/**
 * This class is generated by jOOQ
 */
package br.unb.sma.database.tables;


import br.unb.sma.database.Keys;
import br.unb.sma.database.Procjud;
import br.unb.sma.database.tables.records.TProcessoRelacionadoRecord;
import org.jooq.*;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.List;


/**
 * Identifica conexao entre Processos de forma a permitir a Distribuicao por 
 * dependência
 */
@Generated(
		value = {
				"http://www.jooq.org",
				"jOOQ version:3.7.3"
		},
		comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class TProcessoRelacionado extends TableImpl<TProcessoRelacionadoRecord> {

	/**
	 * The reference instance of <code>procjud.t_processo_relacionado</code>
	 */
	public static final TProcessoRelacionado T_PROCESSO_RELACIONADO = new TProcessoRelacionado();
	private static final long serialVersionUID = -45938453;
	/**
	 * The column <code>procjud.t_processo_relacionado.cod_processo</code>. Codigo numerico atribuido ao processo para identifica-lo no banco de dados
	 */
	public final TableField<TProcessoRelacionadoRecord, Long> COD_PROCESSO = createField("cod_processo", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "Codigo numerico atribuido ao processo para identifica-lo no banco de dados");
	/**
	 * The column <code>procjud.t_processo_relacionado.cod_processo_rel</code>. Codigo numerico atribuido ao processo relacionado
	 */
	public final TableField<TProcessoRelacionadoRecord, Long> COD_PROCESSO_REL = createField("cod_processo_rel", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "Codigo numerico atribuido ao processo relacionado");
	/**
	 * The column <code>procjud.t_processo_relacionado.cod_tipo_relac</code>. Codigo do tipo de relacionamento
	 */
	public final TableField<TProcessoRelacionadoRecord, Byte> COD_TIPO_RELAC = createField("cod_tipo_relac", org.jooq.impl.SQLDataType.TINYINT.nullable(false), this, "Codigo do tipo de relacionamento");

	/**
	 * Create a <code>procjud.t_processo_relacionado</code> table reference
	 */
	public TProcessoRelacionado() {
		this("t_processo_relacionado", null);
	}

	/**
	 * Create an aliased <code>procjud.t_processo_relacionado</code> table reference
	 */
	public TProcessoRelacionado(String alias) {
		this(alias, T_PROCESSO_RELACIONADO);
	}

	private TProcessoRelacionado(String alias, Table<TProcessoRelacionadoRecord> aliased) {
		this(alias, aliased, null);
	}

	private TProcessoRelacionado(String alias, Table<TProcessoRelacionadoRecord> aliased, Field<?>[] parameters) {
		super(alias, Procjud.PROCJUD, aliased, parameters, "Identifica conexao entre Processos de forma a permitir a Distribuicao por dependência");
	}

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<TProcessoRelacionadoRecord> getRecordType() {
		return TProcessoRelacionadoRecord.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<TProcessoRelacionadoRecord> getPrimaryKey() {
		return Keys.PROCESSO_RELACIONADO_PK;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<TProcessoRelacionadoRecord>> getKeys() {
		return Arrays.<UniqueKey<TProcessoRelacionadoRecord>>asList(Keys.PROCESSO_RELACIONADO_PK);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<TProcessoRelacionadoRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<TProcessoRelacionadoRecord, ?>>asList(Keys.T_PROCESSO_RELACIONADO__PROCESSO_RELACIONAMENTO_FK1, Keys.T_PROCESSO_RELACIONADO__PROCESSO_RELACIONAMENTO_FK2, Keys.T_PROCESSO_RELACIONADO__PROCESSO_RELACIONAMENTO_FK3);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TProcessoRelacionado as(String alias) {
		return new TProcessoRelacionado(alias, this);
	}

	/**
	 * Rename this table
	 */
	public TProcessoRelacionado rename(String name) {
		return new TProcessoRelacionado(name, null);
	}
}
