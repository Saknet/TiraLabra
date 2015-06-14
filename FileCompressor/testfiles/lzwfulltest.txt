Lempel–Ziv–Welch (LZW) is a universal lossless data compression algorithm created by Abraham Lempel, Jacob Ziv, and Terry Welch. It was published by Welch in 1984 as an improved implementation of the LZ78 algorithm published by Lempel and Ziv in 1978. The algorithm is simple to implement, and has the potential for very high throughput in hardware implementations.[1] It was the algorithm of the widely used Unix file compression utility compress, and is used in the GIF image format.

Contents  [hide] 
1 Algorithm
1.1 Encoding
1.2 Decoding
1.3 Variable-width codes
1.4 Packing order
2 Example
2.1 Encoding
2.2 Decoding
3 Further coding
4 Uses
5 Patents
6 Variants
7 See also
8 References
9 External links
Algorithm[edit]
The scenario described by Welch's 1984 paper[1] encodes sequences of 8-bit data as fixed-length 12-bit codes. The codes from 0 to 255 represent 1-character sequences consisting of the corresponding 8-bit character, and the codes 256 through 4095 are created in a dictionary for sequences encountered in the data as it is encoded. At each stage in compression, input bytes are gathered into a sequence until the next character would make a sequence for which there is no code yet in the dictionary. The code for the sequence (without that character) is added to the output, and a new code (for the sequence with that character is added to the dictionary.

The idea was quickly adapted to other situations. In an image based on a color table, for example, the natural character alphabet is the set of color table indexes, and in the 1980s, many images had small color tables (on the order of 16 colors). For such a reduced alphabet, the full 12-bit codes yielded poor compression unless the image was large, so the idea of a variable-width code was introduced: codes typically start one bit wider than the symbols being encoded, and as each code size is used up, the code width increases by 1 bit, up to some prescribed maximum (typically 12 bits).

Further refinements include reserving a code to indicate that the code table should be cleared (a "clear code", typically the first value immediately after the values for the individual alphabet characters), and a code to indicate the end of data (a "stop code", typically one greater than the clear code). The clear code allows the table to be reinitialized after it fills up, which lets the encoding adapt to changing patterns in the input data. Smart encoders can monitor the compression efficiency and clear the table whenever the existing table no longer matches the input well.

Since the codes are added in a manner determined by the data, the decoder mimics building the table as it sees the resulting codes. It is critical that the encoder and decoder agree on which variety of LZW is being used: the size of the alphabet, the maximum code width, whether variable-width encoding is being used, the initial code size, whether to use the clear and stop codes (and what values they have). Most formats that employ LZW build this information into the format specification or provide explicit fields for them in a compression header for the data.

Encoding[edit]
A high level view of the encoding algorithm is shown here:

Initialize the dictionary to contain all strings of length one.
Find the longest string W in the dictionary that matches the current input.
Emit the dictionary index for W to output and remove W from the input.
Add W followed by the next symbol in the input to the dictionary.
Go to Step 2.
A dictionary is initialized to contain the single-character strings corresponding to all the possible input characters (and nothing else except the clear and stop codes if they're being used). The algorithm works by scanning through the input string for successively longer substrings until it finds one that is not in the dictionary. When such a string is found, the index for the string without the last character (i.e., the longest substring that is in the dictionary) is retrieved from the dictionary and sent to output, and the new string (including the last character) is added to the dictionary with the next available code. The last input character is then used as the next starting point to scan for substrings.

In this way, successively longer strings are registered in the dictionary and made available for subsequent encoding as single output values. The algorithm works best on data with repeated patterns, so the initial parts of a message will see little compression. As the message grows, however, the compression ratio tends asymptotically to the maximum.[2][clarification needed]

Decoding[edit]
The decoding algorithm works by reading a value from the encoded input and outputting the corresponding string from the initialized dictionary. In order to rebuild the dictionary in the same way as it was built during encoding, it also obtains the next value from the input and adds to the dictionary the concatenation of the current string and the first character of the string obtained by decoding the next input value, or the first character of the string just output if the next value can not be decoded (If the next value is unknown to the decoder, then it must be the value that will be added to the dictionary this iteration, and so its first character must be the same as the first character of the current string being sent to decoded output). The decoder then proceeds to the next input value (which was already read in as the "next value" in the previous pass) and repeats the process until there is no more input, at which point the final input value is decoded without any more additions to the dictionary.

In this way the decoder builds up a dictionary which is identical to that used by the encoder, and uses it to decode subsequent input values. Thus the full dictionary does not need be sent with the encoded data; just the initial dictionary containing the single-character strings is sufficient (and is typically defined beforehand within the encoder and decoder rather than being explicitly sent with the encoded data.)

Variable-width codes[edit]
If variable-width codes are being used, the encoder and decoder must be careful to change the width at the same points in the encoded data, or they will disagree about where the boundaries between individual codes fall in the stream. In the standard version, the encoder increases the width from p to p + 1 when a sequence ω + s is encountered that is not in the table (so that a code must be added for it) but the next available code in the table is 2p (the first code requiring p + 1 bits). The encoder emits the code for ω at width p (since that code does not require p + 1 bits), and then increases the code width so that the next code emitted will be p + 1 bits wide.

The decoder is always one code behind the encoder in building the table, so when it sees the code for ω, it will generate an entry for code 2p − 1. Since this is the point where the encoder will increase the code width, the decoder must increase the width here as well: at the point where it generates the largest code that will fit in p bits.

Unfortunately some early implementations of the encoding algorithm increase the code width and then emit ω at the new width instead of the old width, so that to the decoder it looks like the width changes one code too early. This is called "Early Change"; it caused so much confusion that Adobe now allows both versions in PDF files, but includes an explicit flag in the header of each LZW-compressed stream to indicate whether Early Change is being used. Out of graphics file formats capable of using LZW compression, TIFF uses early change, while GIF and most others don't.

When the table is cleared in response to a clear code, both encoder and decoder change the code width after the clear code back to the initial code width, starting with the code immediately following the clear code.

Packing order[edit]
Since the codes emitted typically do not fall on byte boundaries, the encoder and decoder must agree on how codes are packed into bytes. The two common methods are LSB-First ("Least Significant Bit First") and MSB-First ("Most Significant Bit First"). In LSB-First packing, the first code is aligned so that the least significant bit of the code falls in the least significant bit of the first stream byte, and if the code has more than 8 bits, the high order bits left over are aligned with the least significant bits of the next byte; further codes are packed with LSB going into the least significant bit not yet used in the current stream byte, proceeding into further bytes as necessary. MSB-first packing aligns the first code so that its most significant bit falls in the MSB of the first stream byte, with overflow aligned with the MSB of the next byte; further codes are written with MSB going into the most significant bit not yet used in the current stream byte.

GIF files use LSB-First packing order. TIFF files and PDF files use MSB-First packing order.

Example[edit]
The following example illustrates the LZW algorithm in action, showing the status of the output and the dictionary at every stage, both in encoding and decoding the data. This example has been constructed to give reasonable compression on a very short message. In real text data, repetition is generally less pronounced, so longer input streams are typically necessary before the compression builds up efficiency.

The plaintext to be encoded (from an alphabet using only the capital letters) is:

TOBEORNOTTOBEORTOBEORNOT#
The # is a marker used to show that the end of the message has been reached. There are thus 26 symbols in the plaintext alphabet (the 26 capital letters A through Z), plus the stop code #. We arbitrarily assign these the values 1 through 26 for the letters, and 0 for '#'. (Most flavors of LZW would put the stop code after the data alphabet, but nothing in the basic algorithm requires that. The encoder and decoder only have to agree what value it has.)

A computer will render these as strings of bits. Five-bit codes are needed to give sufficient combinations to encompass this set of 27 values. The dictionary is initialized with these 27 values. As the dictionary grows, the codes will need to grow in width to accommodate the additional entries. A 5-bit code gives 25 = 32 possible combinations of bits, so when the 33rd dictionary word is created, the algorithm will have to switch at that point from 5-bit strings to 6-bit strings (for all code values, including those which were previously output with only five bits). Note that since the all-zero code 00000 is used, and is labeled "0", the 33rd dictionary entry will be labeled 32. (Previously generated output is not affected by the code-width change, but once a 6-bit value is generated in the dictionary, it could conceivably be the next code emitted, so the width for subsequent output shifts to 6 bits to accommodate that.)

The initial dictionary, then, will consist of the following entries
