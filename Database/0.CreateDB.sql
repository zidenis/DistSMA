drop table PROCJUD.T_ADVOGADO;

drop index CLASSE_PROCESSUAL_IN1;

drop table PROCJUD.T_CLASSE_PROCESSUAL;

drop table PROCJUD.T_COMPETENCIA;

drop table PROCJUD.T_COMPOSICAO_OJ;

drop table PROCJUD.T_DENOMINACAO;

drop table PROCJUD.T_DEN_PARTE_FASE_PROC;

drop table PROCJUD.T_DISTRIBUIDOR;

drop table PROCJUD.T_FASE_PROCESSUAL;

drop table PROCJUD.T_HIST_DISTRIBUICAO;

drop table PROCJUD.T_IMPEDIMENTO_ADVOGADO;

drop table PROCJUD.T_IMPEDIMENTO_PARTE;

drop table PROCJUD.T_IMPEDIMENTO_PROCESSO;

drop table PROCJUD.T_MAGISTRADO;

drop table PROCJUD.T_MOTIVO_REDISTRIBUICAO;

drop table PROCJUD.T_ORGAO_JUDICANTE;

drop table PROCJUD.T_PARTE;

drop table PROCJUD.T_PROCESSO;

drop table PROCJUD.T_PROCESSO_PARTE;

drop table PROCJUD.T_PROCESSO_PARTE_ADVOGADO;

drop table PROCJUD.T_PROCESSO_PARTE_PROCURADOR;

drop table PROCJUD.T_PROCESSO_RELACIONADO;

drop table PROCJUD.T_PROCURADOR;

drop table PROCJUD.T_TIPO_DIST;

drop table PROCJUD.T_TIPO_RELACIONAMENTO;

drop schema PROCJUD;

/*==============================================================*/
/* User: PROCJUD                                                */
/*==============================================================*/
create schema PROCJUD authorization PROCJUD;

/*==============================================================*/
/* Table: T_ADVOGADO                                            */
/*==============================================================*/
create table PROCJUD.T_ADVOGADO (
   NUM_ADVOGADO         NUMERIC(8)           not null,
   NOM_ADVOGADO         VARCHAR(64)          not null,
   NUM_OAB              NUMERIC(8)           not null,
   SIG_UF_OAB           CHAR(2)              not null,
   constraint ADVOGADO_PK primary key (NUM_ADVOGADO)
);

comment on table PROCJUD.T_ADVOGADO is
'Advogados que atuam em Processos Judiciais';

comment on column T_ADVOGADO.NUM_ADVOGADO is
'Numero de identificacao do Advogado';

comment on column T_ADVOGADO.NOM_ADVOGADO is
'Nome Completo do Advogado';

comment on column T_ADVOGADO.NUM_OAB is
'Numero de inscricao do advogado na OAB';

comment on column T_ADVOGADO.SIG_UF_OAB is
'Sigla da UF da OAB onde o Advogado esta registrado';

/*==============================================================*/
/* Table: T_CLASSE_PROCESSUAL                                   */
/*==============================================================*/
create table PROCJUD.T_CLASSE_PROCESSUAL (
   SIG_CLASSE           VARCHAR(8)           not null,
   NOM_CLASSE           VARCHAR(64)          not null,
   NUM_CLASSE_CNJ       NUMERIC(5)           not null,
   NOM_POLO_ATIVO       VARCHAR(16)          not null,
   NOM_POLO_PASSIVO     VARCHAR(16)          not null,
   IND_FASE_INICIAL     BOOL                 not null,
   constraint CLASSE_PROCESSUAL_PK primary key (SIG_CLASSE)
);

comment on table PROCJUD.T_CLASSE_PROCESSUAL is
'Classes Processuais classificam o processo judicial de acordo com o tipo de causa que se discute';

comment on column T_CLASSE_PROCESSUAL.SIG_CLASSE is
'Sigla identificadora da classe';

comment on column T_CLASSE_PROCESSUAL.NOM_CLASSE is
'Nome completo da classe';

comment on column T_CLASSE_PROCESSUAL.NUM_CLASSE_CNJ is
'Numeracao da classe segundo classificacao do CNJ';

comment on column T_CLASSE_PROCESSUAL.NOM_POLO_ATIVO is
'Designacao dada ao polo ativo da causa (o autor)';

comment on column T_CLASSE_PROCESSUAL.NOM_POLO_PASSIVO is
'Designacao dada ao polo passivo da causa (o reu)';

comment on column T_CLASSE_PROCESSUAL.IND_FASE_INICIAL is
'Indica se a classe e atribuida na fase inicial do processo, ou diz respeito a fases de recursos';

/*==============================================================*/
/* Index: CLASSE_PROCESSUAL_IN1                                 */
/*==============================================================*/
create unique index CLASSE_PROCESSUAL_IN1 on T_CLASSE_PROCESSUAL (
NOM_CLASSE
);

/*==============================================================*/
/* Table: T_COMPETENCIA                                         */
/*==============================================================*/
create table PROCJUD.T_COMPETENCIA (
   SIG_CLASSE           VARCHAR(8)           not null,
   COD_OJ               NUMERIC(4)           not null,
   constraint MAPEAMENTO_DISTRIBUICAO_PK primary key (SIG_CLASSE, COD_OJ)
);

comment on table PROCJUD.T_COMPETENCIA is
'Mapeamento entre as Classes Processuais e os Orgaos Judicantes para onde processos da classe podem ser distribuidos';

comment on column T_COMPETENCIA.SIG_CLASSE is
'Sigla identificadora da classe';

comment on column T_COMPETENCIA.COD_OJ is
'Codigo identificador do Orgao Judicante';

/*==============================================================*/
/* Table: T_COMPOSICAO_OJ                                       */
/*==============================================================*/
create table PROCJUD.T_COMPOSICAO_OJ (
   COD_OJ               NUMERIC(4)           not null,
   COD_MAGISTRADO       VARCHAR(4)           not null,
   DTA_INI_ATUACAO      DATE                 not null,
   DTA_TER_ATUACAO      DATE                 null,
   IND_PRESIDENTE       BOOL                 not null,
   constraint COMPOSICAO_OJ_PK primary key (COD_OJ, COD_MAGISTRADO)
);

comment on table PROCJUD.T_COMPOSICAO_OJ is
'Composicao dos Orgaos Judicantes: relaciona os magistrados com os OJs, indicando a data de inicio e termino da atuacao do Magistrados naquele OJ';

comment on column T_COMPOSICAO_OJ.COD_OJ is
'Codigo identificador do Orgao Judicante';

comment on column T_COMPOSICAO_OJ.COD_MAGISTRADO is
'Codigo Identificador do Magistrado';

comment on column T_COMPOSICAO_OJ.DTA_INI_ATUACAO is
'Data de inicio da atuacao do Magistrado no OJ';

comment on column T_COMPOSICAO_OJ.DTA_TER_ATUACAO is
'Data de termino da atuacao do Magistrado no OJ';

comment on column T_COMPOSICAO_OJ.IND_PRESIDENTE is
'Indica se o magistrado e o presidente do respectivo OJ';

/*==============================================================*/
/* Table: T_DENOMINACAO                                         */
/*==============================================================*/
create table PROCJUD.T_DENOMINACAO (
   COD_DENOMINACAO      NUMERIC(3)           not null,
   DES_DENOMINACAO      VARCHAR(32)          not null,
   IND_POLO_ATIVO       BOOL                 not null,
   constraint DENOMINACAO_PK primary key (COD_DENOMINACAO)
);

comment on table PROCJUD.T_DENOMINACAO is
'Indica a denominacao dada a parte no processo. Classificacao da parte quanto sua atuacao no processo.';

comment on column T_DENOMINACAO.COD_DENOMINACAO is
'Codigo da denominacao que classifica a parte';

comment on column T_DENOMINACAO.DES_DENOMINACAO is
'Descricao da denominacao que classifica a parte';

comment on column T_DENOMINACAO.IND_POLO_ATIVO is
'Indica se a denominacao e referente ao polo ativo da causa. Polo passivo e o autor da causa, polo passivo e o reu da causa.';

/*==============================================================*/
/* Table: T_DEN_PARTE_FASE_PROC                                 */
/*==============================================================*/
create table PROCJUD.T_DEN_PARTE_FASE_PROC (
   COD_PROCESSO         NUMERIC(11)          not null,
   COD_PARTE            NUMERIC(10)          not null,
   DTA_INICIO_FASE      DATE                 not null,
   SIG_CLASSE           VARCHAR(8)           not null,
   COD_DENOMINACAO      NUMERIC(3)           not null,
   constraint PK_T_DEN_PARTE_FASE_PROC primary key (COD_PROCESSO, COD_PARTE, DTA_INICIO_FASE, SIG_CLASSE)
);

comment on table PROCJUD.T_DEN_PARTE_FASE_PROC is
'Denominacao das partes do processo em uma determinada frase. Relaciona as tabela T_FASE_PROCESSUAL com T_PROCESSO_PARTE de forma a indicar qual a forma de atuacao da parte na fase processual determinada.';

comment on column T_DEN_PARTE_FASE_PROC.COD_PROCESSO is
'Codigo numerico atribuido ao processo para identifica-lo no banco de dados';

comment on column T_DEN_PARTE_FASE_PROC.COD_PARTE is
'Codigo identificador da parte';

comment on column T_DEN_PARTE_FASE_PROC.DTA_INICIO_FASE is
'Data de inicio da fase';

comment on column T_DEN_PARTE_FASE_PROC.SIG_CLASSE is
'Sigla identificadora da classe processual para a respectiva fase';

comment on column T_DEN_PARTE_FASE_PROC.COD_DENOMINACAO is
'Codigo da denominacao que classifica a parte';

/*==============================================================*/
/* Table: T_DISTRIBUIDOR                                        */
/*==============================================================*/
create table PROCJUD.T_DISTRIBUIDOR (
   COD_DISTRIBUIDOR     CHAR(8)              not null,
   NOM_DISTRIBUIDOR     VARCHAR(64)          not null,
   constraint DISTRIBUIDOR_PK primary key (COD_DISTRIBUIDOR)
);

comment on table PROCJUD.T_DISTRIBUIDOR is
'Responsavel pela realizacao de uma distribuicao de processo';

comment on column T_DISTRIBUIDOR.COD_DISTRIBUIDOR is
'Codigo que identifica o distribuidor do processo';

comment on column T_DISTRIBUIDOR.NOM_DISTRIBUIDOR is
'Nome do distribuidor';

/*==============================================================*/
/* Table: T_FASE_PROCESSUAL                                     */
/*==============================================================*/
create table PROCJUD.T_FASE_PROCESSUAL (
   COD_PROCESSO         NUMERIC(11)          not null,
   DTA_INICIO_FASE      DATE                 not null,
   SIG_CLASSE           VARCHAR(8)           not null,
   DTA_TERMINO_FASE     DATE                 null,
   COD_OJ               NUMERIC(4)           null,
   COD_MAGISTRADO       VARCHAR(4)           null,
   COD_MOTIVO_REDIST    NUMERIC(2)           null,
   constraint PK_T_FASE_PROCESSUAL primary key (COD_PROCESSO, DTA_INICIO_FASE, SIG_CLASSE)
);

comment on table PROCJUD.T_FASE_PROCESSUAL is
'Fases pelas quais passou um processo judicial. Um processo pode passar por varias fases. Para cada fase, pode ser atribuida uma classe processual diferente.';

comment on column T_FASE_PROCESSUAL.COD_PROCESSO is
'Codigo do processo judicial';

comment on column T_FASE_PROCESSUAL.DTA_INICIO_FASE is
'Data de inicio da fase';

comment on column T_FASE_PROCESSUAL.SIG_CLASSE is
'Sigla identificadora da classe processual para a respectiva fase';

comment on column T_FASE_PROCESSUAL.DTA_TERMINO_FASE is
'Data de termino da fase do processo';

comment on column T_FASE_PROCESSUAL.COD_OJ is
'Codigo identificador do Orgao Judicante para onde o processo foi distribuido';

comment on column T_FASE_PROCESSUAL.COD_MAGISTRADO is
'Codigo Identificador do Magistrado para qual o processo foi distribuido. Valor nulo se o processo nao foi distribuido.';

comment on column T_FASE_PROCESSUAL.COD_MOTIVO_REDIST is
'Codigo do motivo da redistribuicao. Valor nulo se o processo nao foi redistribuido na fase.';

/*==============================================================*/
/* Table: T_HIST_DISTRIBUICAO                                   */
/*==============================================================*/
create table PROCJUD.T_HIST_DISTRIBUICAO (
   COD_DISTRIBUIDOR     CHAR(8)              not null,
   SEQ_DISTRIBUICAO     NUMERIC(8)           not null,
   COD_PROCESSO         NUMERIC(11)          not null,
   COD_TIPO_DIST        CHAR(1)              not null,
   DTA_DISTRIBUICAO     DATE                 not null,
   COD_OJ               NUMERIC(4)           not null,
   COD_MAGISTRADO       VARCHAR(4)           not null,
   TXT_DISTRIBUICAO     VARCHAR(2048)        not null,
   constraint HIST_DISTRIBUICAO_PK primary key (COD_DISTRIBUIDOR, SEQ_DISTRIBUICAO)
);

comment on table PROCJUD.T_HIST_DISTRIBUICAO is
'Historico de distribuicões realizadas';

comment on column T_HIST_DISTRIBUICAO.COD_DISTRIBUIDOR is
'Codigo que identifica o distribuidor do processo';

comment on column T_HIST_DISTRIBUICAO.SEQ_DISTRIBUICAO is
'Numero sequencial da distribuicao de acordo com o Distribuidor';

comment on column T_HIST_DISTRIBUICAO.COD_PROCESSO is
'Codigo numerico do processo distribuido';

comment on column T_HIST_DISTRIBUICAO.COD_TIPO_DIST is
'Codigo do tipo de distribuicao';

comment on column T_HIST_DISTRIBUICAO.DTA_DISTRIBUICAO is
'Data de realizacao da distribuicao do processo';

comment on column T_HIST_DISTRIBUICAO.COD_OJ is
'Codigo identificador do Orgao Judicante para qual o processo foi distribuido';

comment on column T_HIST_DISTRIBUICAO.COD_MAGISTRADO is
'Codigo Identificador do Magistrado para qual o processo foi distribuido';

comment on column T_HIST_DISTRIBUICAO.TXT_DISTRIBUICAO is
'Registra detalhes da distribuicao para fins de posterior analise';

/*==============================================================*/
/* Table: T_IMPEDIMENTO_ADVOGADO                                */
/*==============================================================*/
create table PROCJUD.T_IMPEDIMENTO_ADVOGADO (
   COD_MAGISTRADO       VARCHAR(4)           not null,
   NUM_ADVOGADO         NUMERIC(8)           not null,
   DTA_REGISTRO         DATE                 not null default CURRENT_DATE,
   constraint IMPEDIMENTO_ADVOGADO_PK primary key (COD_MAGISTRADO, NUM_ADVOGADO)
);

comment on table PROCJUD.T_IMPEDIMENTO_ADVOGADO is
'Impedimento de Magistrados em relacao a Advogados que atuam em processos';

comment on column T_IMPEDIMENTO_ADVOGADO.COD_MAGISTRADO is
'Codigo Identificador do Magistrado';

comment on column T_IMPEDIMENTO_ADVOGADO.NUM_ADVOGADO is
'Numero de identificacao do Advogado';

comment on column T_IMPEDIMENTO_ADVOGADO.DTA_REGISTRO is
'Data de registro do impedimento';

/*==============================================================*/
/* Table: T_IMPEDIMENTO_PARTE                                   */
/*==============================================================*/
create table PROCJUD.T_IMPEDIMENTO_PARTE (
   COD_MAGISTRADO       VARCHAR(4)           not null,
   COD_PARTE            NUMERIC(10)          not null,
   DTA_REGISTRO         DATE                 not null default CURRENT_DATE,
   constraint IMPEDIMENTO_PARTE_PK primary key (COD_MAGISTRADO, COD_PARTE)
);

comment on table PROCJUD.T_IMPEDIMENTO_PARTE is
'Impedimento de Magistrados em relacao as Partes de processos';

comment on column T_IMPEDIMENTO_PARTE.COD_MAGISTRADO is
'Codigo Identificador do Magistrado';

comment on column T_IMPEDIMENTO_PARTE.COD_PARTE is
'Codigo identificador da parte';

comment on column T_IMPEDIMENTO_PARTE.DTA_REGISTRO is
'Data de registro do impedimento';

/*==============================================================*/
/* Table: T_IMPEDIMENTO_PROCESSO                                */
/*==============================================================*/
create table PROCJUD.T_IMPEDIMENTO_PROCESSO (
   COD_MAGISTRADO       VARCHAR(4)           not null,
   COD_PROCESSO         NUMERIC(11)          not null,
   DTA_REGISTRO         DATE                 not null default CURRENT_DATE,
   constraint IMPEDIMENTO_PROCESSO_PK primary key (COD_MAGISTRADO, COD_PROCESSO)
);

comment on table PROCJUD.T_IMPEDIMENTO_PROCESSO is
'Impedimento de Magistrados em relacao a Processos especificos';

comment on column T_IMPEDIMENTO_PROCESSO.COD_MAGISTRADO is
'Codigo Identificador do Magistrado';

comment on column T_IMPEDIMENTO_PROCESSO.COD_PROCESSO is
'Codigo numerico atribuido ao processo para identifica-lo no banco de dados';

comment on column T_IMPEDIMENTO_PROCESSO.DTA_REGISTRO is
'Data de registro do impedimento';

/*==============================================================*/
/* Table: T_MAGISTRADO                                          */
/*==============================================================*/
create table PROCJUD.T_MAGISTRADO (
   COD_MAGISTRADO       VARCHAR(4)           not null,
   NOM_MAGISTRADO       VARCHAR(64)          not null,
   constraint MAGISTRADO_PK primary key (COD_MAGISTRADO)
);

comment on table PROCJUD.T_MAGISTRADO is
'Magistrados que atuam em Orgaos Judicantes para julgar processos';

comment on column T_MAGISTRADO.COD_MAGISTRADO is
'Codigo Identificador do Magistrado';

comment on column T_MAGISTRADO.NOM_MAGISTRADO is
'Nome Completo do Magistrado';

/*==============================================================*/
/* Table: T_MOTIVO_REDISTRIBUICAO                               */
/*==============================================================*/
create table PROCJUD.T_MOTIVO_REDISTRIBUICAO (
   COD_MOTIVO_REDIST    NUMERIC(2)           not null,
   DES_MOTIVO_REDIST    VARCHAR(64)          not null,
   constraint MOTIVO_REDISTRIBUICAO_PK primary key (COD_MOTIVO_REDIST)
);

comment on table PROCJUD.T_MOTIVO_REDISTRIBUICAO is
'Relaciona os possiveis motivos para redistribuicao de um processo';

comment on column T_MOTIVO_REDISTRIBUICAO.COD_MOTIVO_REDIST is
'Codigo do motivo da redistribuicao';

comment on column T_MOTIVO_REDISTRIBUICAO.DES_MOTIVO_REDIST is
'Relaciona os possiveis motivos para redistribuicao do processo';

/*==============================================================*/
/* Table: T_ORGAO_JUDICANTE                                     */
/*==============================================================*/
create table PROCJUD.T_ORGAO_JUDICANTE (
   COD_OJ               NUMERIC(4)           not null,
   SIG_OJ               VARCHAR(16)          not null,
   DES_OJ               VARCHAR(64)          not null,
   constraint ORGAO_JUDICANTE_PK primary key (COD_OJ)
);

comment on table PROCJUD.T_ORGAO_JUDICANTE is
'Entidade diretamente responsavel por conduzir e decidir um determinado processo judicial';

comment on column T_ORGAO_JUDICANTE.COD_OJ is
'Codigo identificador do Orgao Judicante';

comment on column T_ORGAO_JUDICANTE.SIG_OJ is
'Sigla identificadora do Orgao Judicante';

comment on column T_ORGAO_JUDICANTE.DES_OJ is
'Descricao do Orgao Judicante';

/*==============================================================*/
/* Table: T_PARTE                                               */
/*==============================================================*/
create table PROCJUD.T_PARTE (
   COD_PARTE            NUMERIC(10)          not null,
   NOM_PARTE            VARCHAR(64)          not null,
   TIP_PARTE            CHAR(1)              not null,
   NUM_CNPJ             NUMERIC(14)          not null,
   NUM_CPF              NUMERIC(11)          not null,
   constraint PARTE_PK primary key (COD_PARTE)
);

comment on table PROCJUD.T_PARTE is
'Partes do Processo Judicial';

comment on column T_PARTE.COD_PARTE is
'Codigo identificador da parte';

comment on column T_PARTE.NOM_PARTE is
'Nome completo da parte';

comment on column T_PARTE.TIP_PARTE is
'Tipo da Parte: F - pessoa Fisica, J - pessoa Juridica';

comment on column T_PARTE.NUM_CNPJ is
'Numero do CNPJ se pessoa juridica';

comment on column T_PARTE.NUM_CPF is
'Numero do CPF se pessoa fisica';

/*==============================================================*/
/* Table: T_PROCESSO                                            */
/*==============================================================*/
create table PROCJUD.T_PROCESSO (
   COD_PROCESSO         NUMERIC(11)          not null,
   NUM_PROCESSO         NUMERIC(7)           not null,
   NUM_DIGITO           NUMERIC(2)           not null,
   ANO_PROCESSO         NUMERIC(4)           not null,
   NUM_SEGMENTO         NUMERIC(1)           not null,
   NUM_TRIBUNAL         NUMERIC(2)           not null,
   NUM_ORIGEM           NUMERIC(4)           not null,
   DTA_AUTUACAO         DATE                 not null,
   constraint PROCESSO_PK primary key (COD_PROCESSO)
);

comment on table PROCJUD.T_PROCESSO is
'Processos Judiciais Autuados. Os processos possuem uma identificacao interna COD_PROCESSO utilizada no banco de dados e uma identificacao padrao (numeracao unica) utilizada em todos os ramos do Poder Judiciario. A identificacao padrao e utilizada desde jan/2010 no TST e possui o formato numerico de 20 digitos NNNNNNN-DD.AAAA.J.TR.OOOO';

comment on column T_PROCESSO.COD_PROCESSO is
'Codigo numerico atribuido ao processo para identifica-lo no banco de dados';

comment on column T_PROCESSO.NUM_PROCESSO is
'NNNNNNN -  numero de ordem de autuacao do processo, no ano de autuacao e na unidade jurisdicional de origem';

comment on column T_PROCESSO.NUM_DIGITO is
'DD - digitos verificadores da integridade do numero do processo.';

comment on column T_PROCESSO.ANO_PROCESSO is
'AAAA - ano da ajuizamento do processo';

comment on column T_PROCESSO.NUM_SEGMENTO is
'J - digito identificador do segmento do Judiciario a que pertence o processo. 1 - STF, 2 - CNJ, 3 - STJ, 4 - JF, 5 - JT, 6 - JE, 7 - JM da Uniao, 8 - Justica dos Estados e do DF, 9 JM Estadual';

comment on column T_PROCESSO.NUM_TRIBUNAL is
'TR - tribunal ou conselho do segmento do Poder Judiciario a que pertence o processo; para os tribunais superiores (STF, STJ, TST, TSE e STM) e o CNJ, o codigo devera ser preenchido com zero (00), para os Conselhos da Justica Federal e Superior da Justica do Trabalho, devera ser preenchido com o numero 90 (noventa), para os demais tribunais, com um numero identificador do tribunal';

comment on column T_PROCESSO.NUM_ORIGEM is
'OOOO - unidade de origem do processo, seguindo regras diversas para cada um dos segmentos do Judiciario, a excecao dos tribunais e conselhos, que terao esses digitos preenchidos com zero (0000)';

comment on column T_PROCESSO.DTA_AUTUACAO is
'Data de autuacao do processo';

/*==============================================================*/
/* Table: T_PROCESSO_PARTE                                      */
/*==============================================================*/
create table PROCJUD.T_PROCESSO_PARTE (
   COD_PROCESSO         NUMERIC(11)          not null,
   COD_PARTE            NUMERIC(10)          not null,
   constraint PROCESSO_PARTE_PK primary key (COD_PROCESSO, COD_PARTE)
);

comment on table PROCJUD.T_PROCESSO_PARTE is
'Relaciona as Partes interessadas em Processos Judiciais';

comment on column T_PROCESSO_PARTE.COD_PROCESSO is
'Codigo numerico atribuido ao processo para identifica-lo no banco de dados';

comment on column T_PROCESSO_PARTE.COD_PARTE is
'Codigo identificador da parte';

/*==============================================================*/
/* Table: T_PROCESSO_PARTE_ADVOGADO                             */
/*==============================================================*/
create table PROCJUD.T_PROCESSO_PARTE_ADVOGADO (
   COD_PROCESSO         NUMERIC(11)          not null,
   COD_PARTE            NUMERIC(10)          not null,
   NUM_ADVOGADO         NUMERIC(8)           not null,
   constraint PROCESSO_PARTE_ADVOGADO_PK primary key (COD_PROCESSO, COD_PARTE, NUM_ADVOGADO)
);

comment on table PROCJUD.T_PROCESSO_PARTE_ADVOGADO is
'Relaciona os Advogados das Partes interessadas em Processos Judiciais';

comment on column T_PROCESSO_PARTE_ADVOGADO.COD_PROCESSO is
'Codigo numerico atribuido ao processo para identifica-lo no banco de dados';

comment on column T_PROCESSO_PARTE_ADVOGADO.COD_PARTE is
'Codigo identificador da parte';

comment on column T_PROCESSO_PARTE_ADVOGADO.NUM_ADVOGADO is
'Numero de identificacao do Advogado';

/*==============================================================*/
/* Table: T_PROCESSO_PARTE_PROCURADOR                           */
/*==============================================================*/
create table PROCJUD.T_PROCESSO_PARTE_PROCURADOR (
   COD_PROCESSO         NUMERIC(11)          not null,
   COD_PARTE            NUMERIC(10)          not null,
   NUM_PROCURADOR       NUMERIC(8)           not null,
   constraint PROCESSO_PARTE_PROCURADOR_PK primary key (COD_PROCESSO, COD_PARTE, NUM_PROCURADOR)
);

comment on table PROCJUD.T_PROCESSO_PARTE_PROCURADOR is
'Relaciona os Procuradores das Partes interessadas em Processos Judiciais';

comment on column T_PROCESSO_PARTE_PROCURADOR.COD_PROCESSO is
'Codigo numerico atribuido ao processo para identifica-lo no banco de dados';

comment on column T_PROCESSO_PARTE_PROCURADOR.COD_PARTE is
'Codigo identificador da parte';

comment on column T_PROCESSO_PARTE_PROCURADOR.NUM_PROCURADOR is
'Numero identificador do procurador';

/*==============================================================*/
/* Table: T_PROCESSO_RELACIONADO                                */
/*==============================================================*/
create table PROCJUD.T_PROCESSO_RELACIONADO (
   COD_PROCESSO         NUMERIC(11)          not null,
   COD_PROCESSO_REL     NUMERIC(11)          not null,
   COD_TIPO_RELAC       NUMERIC(2)           not null,
   constraint PROCESSO_RELACIONADO_PK primary key (COD_PROCESSO, COD_PROCESSO_REL)
);

comment on table PROCJUD.T_PROCESSO_RELACIONADO is
'Identifica conexao entre Processos de forma a permitir a Distribuicao por dependência';

comment on column T_PROCESSO_RELACIONADO.COD_PROCESSO is
'Codigo numerico atribuido ao processo para identifica-lo no banco de dados';

comment on column T_PROCESSO_RELACIONADO.COD_PROCESSO_REL is
'Codigo numerico atribuido ao processo relacionado';

comment on column T_PROCESSO_RELACIONADO.COD_TIPO_RELAC is
'Codigo do tipo de relacionamento';

/*==============================================================*/
/* Table: T_PROCURADOR                                          */
/*==============================================================*/
create table PROCJUD.T_PROCURADOR (
   NUM_PROCURADOR       NUMERIC(8)           not null,
   NOM_PROCURADOR       VARCHAR(64)          not null,
   NOM_PROCURADORIA     VARCHAR(32)          not null,
   constraint PROCURADOR_PK primary key (NUM_PROCURADOR)
);

comment on table PROCJUD.T_PROCURADOR is
'Procuradores que atuam em Processos Judiciais';

comment on column T_PROCURADOR.NUM_PROCURADOR is
'Numero identificador do procurador';

comment on column T_PROCURADOR.NOM_PROCURADOR is
'Nome completo do procurador';

comment on column T_PROCURADOR.NOM_PROCURADORIA is
'Nome da procuradoria pela qual o procurador atua';

/*==============================================================*/
/* Table: T_TIPO_DIST                                           */
/*==============================================================*/
create table PROCJUD.T_TIPO_DIST (
   COD_TIPO_DIST        CHAR(1)              not null,
   DES_TIPO_DIST        VARCHAR(32)          not null,
   constraint TIPO_DIST_PK primary key (COD_TIPO_DIST)
);

comment on table PROCJUD.T_TIPO_DIST is
'Tipos de Distribuicao';

comment on column T_TIPO_DIST.COD_TIPO_DIST is
'Codigo do tipo de distribuicao';

comment on column T_TIPO_DIST.DES_TIPO_DIST is
'Descricao do tipo de distribuicao';

/*==============================================================*/
/* Table: T_TIPO_RELACIONAMENTO                                 */
/*==============================================================*/
create table PROCJUD.T_TIPO_RELACIONAMENTO (
   COD_TIPO_RELAC       NUMERIC(2)           not null,
   DES_TIPO_RELAC       VARCHAR(16)          not null,
   constraint TIPO_RELACIONAMENTO_PK primary key (COD_TIPO_RELAC)
);

comment on table PROCJUD.T_TIPO_RELACIONAMENTO is
'Ttipos de relacionamento entre dois processos';

comment on column T_TIPO_RELACIONAMENTO.COD_TIPO_RELAC is
'Codigo do tipo de relacionamento';

comment on column T_TIPO_RELACIONAMENTO.DES_TIPO_RELAC is
'Descricao do tipo de relacionamento';

alter table T_COMPETENCIA
   add constraint MAPEAMENTO_DISTRIBUICAO_FK1 foreign key (SIG_CLASSE)
      references T_CLASSE_PROCESSUAL (SIG_CLASSE)
      on delete cascade on update cascade;

alter table T_COMPETENCIA
   add constraint MAPEAMENTO_DISTRIBUICAO_FK2 foreign key (COD_OJ)
      references T_ORGAO_JUDICANTE (COD_OJ)
      on delete cascade on update cascade;

alter table T_COMPOSICAO_OJ
   add constraint COMPOSICAO_OJ_FK1 foreign key (COD_OJ)
      references T_ORGAO_JUDICANTE (COD_OJ)
      on delete cascade on update cascade;

alter table T_COMPOSICAO_OJ
   add constraint COMPOSICAO_OJ_FK2 foreign key (COD_MAGISTRADO)
      references T_MAGISTRADO (COD_MAGISTRADO)
      on delete cascade on update cascade;

alter table T_DEN_PARTE_FASE_PROC
   add constraint DEN_PARTE_FASE_PROC_FK1 foreign key (COD_PROCESSO, COD_PARTE)
      references T_PROCESSO_PARTE (COD_PROCESSO, COD_PARTE)
      on delete restrict on update restrict;

alter table T_DEN_PARTE_FASE_PROC
   add constraint DEN_PARTE_FASE_PROC_FK2 foreign key (COD_PROCESSO, DTA_INICIO_FASE, SIG_CLASSE)
      references T_FASE_PROCESSUAL (COD_PROCESSO, DTA_INICIO_FASE, SIG_CLASSE)
      on delete restrict on update restrict;

alter table T_DEN_PARTE_FASE_PROC
   add constraint DEN_PARTE_FASE_PROC_FK3 foreign key (COD_DENOMINACAO)
      references T_DENOMINACAO (COD_DENOMINACAO)
      on delete restrict on update restrict;

alter table T_FASE_PROCESSUAL
   add constraint FASE_PROCESSUAL_FK5 foreign key (COD_PROCESSO)
      references T_PROCESSO (COD_PROCESSO)
      on delete cascade on update cascade;

alter table T_FASE_PROCESSUAL
   add constraint PROCESSO_FK1 foreign key (SIG_CLASSE)
      references T_CLASSE_PROCESSUAL (SIG_CLASSE)
      on delete restrict on update restrict;

alter table T_FASE_PROCESSUAL
   add constraint PROCESSO_FK2 foreign key (COD_OJ)
      references T_ORGAO_JUDICANTE (COD_OJ)
      on delete set null on update set null;

alter table T_FASE_PROCESSUAL
   add constraint PROCESSO_FK3 foreign key (COD_MAGISTRADO)
      references T_MAGISTRADO (COD_MAGISTRADO)
      on delete set null on update set null;

alter table T_FASE_PROCESSUAL
   add constraint PROCESSO_FK4 foreign key (COD_MOTIVO_REDIST)
      references T_MOTIVO_REDISTRIBUICAO (COD_MOTIVO_REDIST)
      on delete restrict on update restrict;

alter table T_HIST_DISTRIBUICAO
   add constraint HIST_DISTRIBUIDOR_FK1 foreign key (COD_DISTRIBUIDOR)
      references T_DISTRIBUIDOR (COD_DISTRIBUIDOR)
      on delete restrict on update restrict;

alter table T_HIST_DISTRIBUICAO
   add constraint DISTRIBUIDOR_FK1 foreign key (COD_TIPO_DIST)
      references T_TIPO_DIST (COD_TIPO_DIST)
      on delete restrict on update restrict;

alter table T_HIST_DISTRIBUICAO
   add constraint HIST_DISTRIBUIDOR_FK3 foreign key (COD_PROCESSO)
      references T_PROCESSO (COD_PROCESSO)
      on delete cascade on update cascade;

alter table T_HIST_DISTRIBUICAO
   add constraint HIST_DISTRIBUIDOR_FK4 foreign key (COD_MAGISTRADO)
      references T_MAGISTRADO (COD_MAGISTRADO)
      on delete restrict on update restrict;

alter table T_HIST_DISTRIBUICAO
   add constraint HIST_DISTRIBUIDOR_FK5 foreign key (COD_OJ)
      references T_ORGAO_JUDICANTE (COD_OJ)
      on delete restrict on update restrict;

alter table T_IMPEDIMENTO_ADVOGADO
   add constraint IMPEDIMENTO_ADVOGADO_FK1 foreign key (COD_MAGISTRADO)
      references T_MAGISTRADO (COD_MAGISTRADO)
      on delete cascade on update cascade;

alter table T_IMPEDIMENTO_ADVOGADO
   add constraint IMPEDIMENTO_ADVOGADO_FK2 foreign key (NUM_ADVOGADO)
      references T_ADVOGADO (NUM_ADVOGADO)
      on delete cascade on update cascade;

alter table T_IMPEDIMENTO_PARTE
   add constraint IMPEDIMENTO_PARTE_FK1 foreign key (COD_MAGISTRADO)
      references T_MAGISTRADO (COD_MAGISTRADO)
      on delete cascade on update cascade;

alter table T_IMPEDIMENTO_PARTE
   add constraint IMPEDIMENTO_PARTE_FK2 foreign key (COD_PARTE)
      references T_PARTE (COD_PARTE)
      on delete cascade on update cascade;

alter table T_IMPEDIMENTO_PROCESSO
   add constraint IMPEDIMENTO_PROCESSO_FK1 foreign key (COD_MAGISTRADO)
      references T_MAGISTRADO (COD_MAGISTRADO)
      on delete cascade on update cascade;

alter table T_IMPEDIMENTO_PROCESSO
   add constraint IMPEDIMENTO_PROCESSO_FK2 foreign key (COD_PROCESSO)
      references T_PROCESSO (COD_PROCESSO)
      on delete cascade on update cascade;

alter table T_PROCESSO_PARTE
   add constraint PROCESSO_PARTE_FK1 foreign key (COD_PROCESSO)
      references T_PROCESSO (COD_PROCESSO)
      on delete cascade on update cascade;

alter table T_PROCESSO_PARTE
   add constraint PROCESSO_PARTE_FK2 foreign key (COD_PARTE)
      references T_PARTE (COD_PARTE)
      on delete restrict on update restrict;

alter table T_PROCESSO_PARTE_ADVOGADO
   add constraint PROCESSO_PARTE_ADVOGADO_FK1 foreign key (COD_PROCESSO, COD_PARTE)
      references T_PROCESSO_PARTE (COD_PROCESSO, COD_PARTE)
      on delete cascade on update cascade;

alter table T_PROCESSO_PARTE_ADVOGADO
   add constraint PROCESSO_PARTE_ADVOGADO_FK2 foreign key (NUM_ADVOGADO)
      references T_ADVOGADO (NUM_ADVOGADO)
      on delete cascade on update cascade;

alter table T_PROCESSO_PARTE_PROCURADOR
   add constraint PROCESSO_PARTE_PROCURADOR_FK1 foreign key (COD_PROCESSO, COD_PARTE)
      references T_PROCESSO_PARTE (COD_PROCESSO, COD_PARTE)
      on delete cascade on update cascade;

alter table T_PROCESSO_PARTE_PROCURADOR
   add constraint PROCESSO_PARTE_PROCURADOR_FK2 foreign key (NUM_PROCURADOR)
      references T_PROCURADOR (NUM_PROCURADOR)
      on delete cascade on update cascade;

alter table T_PROCESSO_RELACIONADO
   add constraint PROCESSO_RELACIONAMENTO_FK1 foreign key (COD_PROCESSO)
      references T_PROCESSO (COD_PROCESSO)
      on delete cascade on update cascade;

alter table T_PROCESSO_RELACIONADO
   add constraint PROCESSO_RELACIONAMENTO_FK2 foreign key (COD_PROCESSO_REL)
      references T_PROCESSO (COD_PROCESSO)
      on delete cascade on update cascade;

alter table T_PROCESSO_RELACIONADO
   add constraint PROCESSO_RELACIONAMENTO_FK3 foreign key (COD_TIPO_RELAC)
      references T_TIPO_RELACIONAMENTO (COD_TIPO_RELAC)
      on delete restrict on update restrict;
