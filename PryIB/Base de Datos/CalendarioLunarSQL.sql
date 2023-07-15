/*==============================================================*/
/* DBMS name:      PostgreSQL 7.3                               */
/* Created on:     15/7/2023 13:47:14                           */
/*==============================================================*/


drop index RELATIONSHIP_5_FK;

drop index CONSEJOS_PK;

drop table CONSEJOS;

drop index RELATIONSHIP_2_FK;

drop index EVENTOS_PK;

drop table EVENTOS;

drop index FASES_LUNARES_PK;

drop table FASES_LUNARES;

drop index RELATIONSHIP_3_FK;

drop index GLOSARIO_PK;

drop table GLOSARIO;

/*==============================================================*/
/* Table: CONSEJOS                                              */
/*==============================================================*/
create table CONSEJOS (
IDCONSEJOS           INT4                 not null,
IDFASES              INT4                 null,
DESCRIPCIONCONSEJO   VARCHAR(200)         not null,
constraint PK_CONSEJOS primary key (IDCONSEJOS)
);

/*==============================================================*/
/* Index: CONSEJOS_PK                                           */
/*==============================================================*/
create unique index CONSEJOS_PK on CONSEJOS (
IDCONSEJOS
);

/*==============================================================*/
/* Index: RELATIONSHIP_5_FK                                     */
/*==============================================================*/
create  index RELATIONSHIP_5_FK on CONSEJOS (
IDFASES
);

/*==============================================================*/
/* Table: EVENTOS                                               */
/*==============================================================*/
create table EVENTOS (
IDEVENTO             INT4                 not null,
IDFASES              INT4                 null,
NOMBREEVENTO         VARCHAR(40)          not null,
DESCRIPCIONEVENTO    VARCHAR(200)         not null,
TIPOEVENTO           CHAR(40)             not null,
FECHAEVENTO          DATE                 not null,
HORAEVENTO           TIME                 not null,
constraint PK_EVENTOS primary key (IDEVENTO)
);

/*==============================================================*/
/* Index: EVENTOS_PK                                            */
/*==============================================================*/
create unique index EVENTOS_PK on EVENTOS (
IDEVENTO
);

/*==============================================================*/
/* Index: RELATIONSHIP_2_FK                                     */
/*==============================================================*/
create  index RELATIONSHIP_2_FK on EVENTOS (
IDFASES
);

/*==============================================================*/
/* Table: FASES_LUNARES                                         */
/*==============================================================*/
create table FASES_LUNARES (
IDFASES              INT4                 not null,
NOMBREFASE           VARCHAR(40)          not null,
FECHASINICIOFASE     DATE                 not null,
FECHAFINFASE         DATE                 not null,
CLIMA                VARCHAR(40)          null,
ILUMINACIONLUNA      VARCHAR(40)          null,
constraint PK_FASES_LUNARES primary key (IDFASES)
);

/*==============================================================*/
/* Index: FASES_LUNARES_PK                                      */
/*==============================================================*/
create unique index FASES_LUNARES_PK on FASES_LUNARES (
IDFASES
);

/*==============================================================*/
/* Table: GLOSARIO                                              */
/*==============================================================*/
create table GLOSARIO (
IDGLOSARIO           INT4                 not null,
IDFASES              INT4                 null,
TERMINO              VARCHAR(40)          not null,
SIGNIFICADOTERMINADO VARCHAR(200)         not null,
constraint PK_GLOSARIO primary key (IDGLOSARIO)
);

/*==============================================================*/
/* Index: GLOSARIO_PK                                           */
/*==============================================================*/
create unique index GLOSARIO_PK on GLOSARIO (
IDGLOSARIO
);

/*==============================================================*/
/* Index: RELATIONSHIP_3_FK                                     */
/*==============================================================*/
create  index RELATIONSHIP_3_FK on GLOSARIO (
IDFASES
);

alter table CONSEJOS
   add constraint FK_CONSEJOS_RELATIONS_FASES_LU foreign key (IDFASES)
      references FASES_LUNARES (IDFASES)
      on delete restrict on update restrict;

alter table EVENTOS
   add constraint FK_EVENTOS_RELATIONS_FASES_LU foreign key (IDFASES)
      references FASES_LUNARES (IDFASES)
      on delete restrict on update restrict;

alter table GLOSARIO
   add constraint FK_GLOSARIO_RELATIONS_FASES_LU foreign key (IDFASES)
      references FASES_LUNARES (IDFASES)
      on delete restrict on update restrict;

