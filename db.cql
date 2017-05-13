CREATE KEYSPACE turquas
WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '3'}
AND durable_writes = true;

CREATE TABLE W2V_TOKEN (
    token_name text PRIMARY KEY,
    stem boolean,
    value list<double>,
);

CREATE INDEX is_stem_index ON w2v_token (stem);

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
	questions set<text>,
	stemmed_words_list list<text>,     
	tags set<text>,     
	token_list list<text>,     
	PRIMARY KEY (source_name, original_sentence)
);

CREATE TABLE question (
    source_name text,
    question text,
    noun_clause boolean,
    question_w2v_value_map map<text, frozen <list<double>>>,
    question_word text,
    word_type_after_question_word text,
    answer text,
    answer_w2v_value_map map<text, frozen <list<double>>>,
    PRIMARY KEY (source_name, question)
);