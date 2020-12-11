# TestPojo


## Testing coupons branch
### Sent to source-topic:

{"clientPin":"bcdefg", "reqAmt":"10.0", "merchant":"Cafe&Restaurant", "uTime":"2020-12-08T14:48:23.000"}

{"clientPin":"bcdefg", "reqAmt":"20.0", "merchant":"Cafe&Restaurant", "uTime":"2020-12-08T14:48:23.000"}

{"clientPin":"bcdefg", "reqAmt":"5.0", "merchant":"Supermarket", "uTime":"2020-12-08T14:48:23.000"}

{"clientPin":"bcdefg", "reqAmt":"7.0", "merchant":"Supermarket", "uTime":"2020-12-08T14:48:23.000"}

{"clientPin":"bcdefg", "reqAmt":"30.0", "merchant":"Cafe&Restaurant", "uTime":"2020-12-08T14:48:23.000"}

### Received in coupons:

{"clientPin":"bcdefg","reqAmt":15.0,"merchant":"Cafe\u0026Restaurant + Supermarket"}
{"clientPin":"bcdefg","reqAmt":25.0,"merchant":"Cafe\u0026Restaurant + Supermarket"}
{"clientPin":"bcdefg","reqAmt":17.0,"merchant":"Cafe\u0026Restaurant + Supermarket"}
{"clientPin":"bcdefg","reqAmt":27.0,"merchant":"Cafe\u0026Restaurant + Supermarket"}
{"clientPin":"bcdefg","reqAmt":35.0,"merchant":"Cafe\u0026Restaurant + Supermarket"}
{"clientPin":"bcdefg","reqAmt":37.0,"merchant":"Cafe\u0026Restaurant + Supermarket"}


## Testing cafe transformer
{"clientPin":"abcdef", "reqAmt":"100.0", "merchant":"Cafe&Restaurant", "uTime":"2020-12-08T14:48:23.000"}
{"clientPin":"abcdef", "reqAmt":"200.0", "merchant":"Cafe&Restaurant", "uTime":"2020-12-08T14:48:23.000"}
{"clientPin":"abcdef", "reqAmt":"4000.0", "merchant":"Cafe&Restaurant", "uTime":"2020-12-08T14:48:23.000"}
{"clientPin":"cbcdef", "reqAmt":"10.0", "merchant":"Cafe&Restaurant", "uTime":"2020-12-08T14:48:23.000"}
{"clientPin":"cbcdef", "reqAmt":"20.0", "merchant":"Cafe&Restaurant", "uTime":"2020-12-09T14:48:23.000"}
{"clientPin":"cbcdef", "reqAmt":"30.0", "merchant":"Cafe&Restaurant", "uTime":"2020-12-09T14:48:23.000"}
{"clientPin":"cbcdef", "reqAmt":"3060.0", "merchant":"Cafe&Restaurant", "uTime":"2020-12-10T14:48:23.000"}