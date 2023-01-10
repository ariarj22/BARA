import textblob

def translate(kata, source, to):
    words = textblob.TextBlob(kata)

    words = words.translate(from_lang=source, to=to)

    return words

