/**
 * This class is generated by jOOQ
 */
package br.unb.sma.database.tables;


import br.unb.sma.database.Keys;
import br.unb.sma.database.Procjud;
import br.unb.sma.database.tables.records.TImpedimentoProcessoRecord;
import org.jooq.*;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;


/**
 * Impedimento de Magistrados em relacao a Processos especificos
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.7.3"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class TImpedimentoProcesso extends TableImpl<TImpedimentoProcessoRecord> {

    /**
     * The reference instance of <code>procjud.t_impedimento_processo</code>
     */
    public static final TImpedimentoProcesso T_IMPEDIMENTO_PROCESSO = new TImpedimentoProcesso();
    private static final long serialVersionUID = 1873896174;
    /**
     * The column <code>procjud.t_impedimento_processo.cod_magistrado</code>. Codigo Identificador do Magistrado
     */
    public final TableField<TImpedimentoProcessoRecord, String> COD_MAGISTRADO = createField("cod_magistrado", org.jooq.impl.SQLDataType.VARCHAR.length(4).nullable(false), this, "Codigo Identificador do Magistrado");
    /**
     * The column <code>procjud.t_impedimento_processo.cod_processo</code>. Codigo numerico atribuido ao processo para identifica-lo no banco de dados
     */
    public final TableField<TImpedimentoProcessoRecord, Long> COD_PROCESSO = createField("cod_processo", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "Codigo numerico atribuido ao processo para identifica-lo no banco de dados");
    /**
     * The column <code>procjud.t_impedimento_processo.dta_registro</code>. Data de registro do impedimento
     */
    public final TableField<TImpedimentoProcessoRecord, Date> DTA_REGISTRO = createField("dta_registro", org.jooq.impl.SQLDataType.DATE.nullable(false).defaulted(true), this, "Data de registro do impedimento");

    /**
     * Create a <code>procjud.t_impedimento_processo</code> table reference
     */
    public TImpedimentoProcesso() {
        this("t_impedimento_processo", null);
    }

    /**
     * Create an aliased <code>procjud.t_impedimento_processo</code> table reference
     */
    public TImpedimentoProcesso(String alias) {
        this(alias, T_IMPEDIMENTO_PROCESSO);
    }

    private TImpedimentoProcesso(String alias, Table<TImpedimentoProcessoRecord> aliased) {
        this(alias, aliased, null);
    }

    private TImpedimentoProcesso(String alias, Table<TImpedimentoProcessoRecord> aliased, Field<?>[] parameters) {
        super(alias, Procjud.PROCJUD, aliased, parameters, "Impedimento de Magistrados em relacao a Processos especificos");
    }

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TImpedimentoProcessoRecord> getRecordType() {
        return TImpedimentoProcessoRecord.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TImpedimentoProcessoRecord> getPrimaryKey() {
        return Keys.IMPEDIMENTO_PROCESSO_PK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TImpedimentoProcessoRecord>> getKeys() {
        return Arrays.<UniqueKey<TImpedimentoProcessoRecord>>asList(Keys.IMPEDIMENTO_PROCESSO_PK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TImpedimentoProcessoRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TImpedimentoProcessoRecord, ?>>asList(Keys.T_IMPEDIMENTO_PROCESSO__IMPEDIMENTO_PROCESSO_FK1, Keys.T_IMPEDIMENTO_PROCESSO__IMPEDIMENTO_PROCESSO_FK2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TImpedimentoProcesso as(String alias) {
        return new TImpedimentoProcesso(alias, this);
    }

    /**
     * Rename this table
     */
    public TImpedimentoProcesso rename(String name) {
        return new TImpedimentoProcesso(name, null);
    }
}
