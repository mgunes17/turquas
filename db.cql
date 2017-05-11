CREATE TABLE W2V_TOKEN (
    token_name text,
    is_stem boolean,
    value list<double>,
    primary key(is_stem, token_name)
);

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
    is_analysis_null boolean,
    token_name text,
    analysis set<text>,
    PRIMARY KEY (is_analysis_null, token_name)
);

CREATE TABLE sentence (
    original_sentence text PRIMARY KEY,
    source_name text,
    questions set<text>,
    stemmed_words_list list<text>,
    tags set<text>,
    token_list list<text>
);