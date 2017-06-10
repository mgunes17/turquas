CREATE KEYSPACE turquas
WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '3'}
AND durable_writes = true;

CREATE TABLE w2v_token (
    token_name text,
    type text,
    value list<double>,
    PRIMARY KEY (token_name, type)
);

CREATE INDEX type_index ON w2v_token (type);

CREATE TABLE source (
    source_name text PRIMARY KEY,
    best_words set<text>,
    last_updated_date timestamp,
    word_count_map map<text, int>
);

CREATE TABLE unique_word (
    word text PRIMARY KEY,
    source set<text>,
    value map<text, double>
);

CREATE TABLE token_morph_analysis (
    token_name text PRIMARY KEY,
    analysis_null boolean,
    analysis set<text>
);

CREATE INDEX is_analysis_null_index ON token_morph_analysis (analysis_null);

CREATE TABLE sentence (
    source_name text,
	original_sentence text,
	stemmed_words_list list<text>,
	tags set<text>,     
	token_list list<text>,     
	PRIMARY KEY (original_sentence)
);

CREATE INDEX source_name ON sentence (source_name);

CREATE TABLE question (
    source_name text,
    question text,
    noun_clause boolean,
    processed boolean,
    question_type text,
    question_w2v_value_map map<text, frozen <list<double>>>,
    answer text,
    answer_w2v_value_map map<text, frozen <list<double>>>,
    PRIMARY KEY (source_name, noun_clause, question_type, question)
);

CREATE INDEX is_noun_clause ON question (noun_clause);
CREATE INDEX is_processed ON question (processed);