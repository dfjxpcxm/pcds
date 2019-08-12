/**
 * Copyright(c) 2006-2008, FeyaSoft Inc.
 * ====================================================================
 * Licence
 * ====================================================================
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY 
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE 
 * WARRANTIES OF MERCHANTABILITY,FITNESS FOR A PARTICULAR PURPOSE 
 * AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR 
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR 
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
if (typeof bsbank == "undefined") {
    /**
     * The feyaSoft global package object.  If feyaSoft is already defined, the
     * existing feyaSoft object will not be overwritten so that defined
     * packages are preserved.
     * @class feyaSoft
     * @static
     */
    var bsbank = {};
}

package = function() {
    var a=arguments, o=null, i, j, d;
    for (i=0; i < a.length; i=i+1) {
        d=a[i].split(".");
        o=bsbank;

        // feyaSoft is implied, so it is ignored if it is included
        for (j=(d[0] == "bsbank") ? 1 : 0; j<d.length; j=j+1) {
            o[d[j]]=o[d[j]] || {};
            o=o[d[j]];
        }
    }
    return o;
};
