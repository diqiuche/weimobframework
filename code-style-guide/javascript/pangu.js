(function(pangu) {
    'use strict';

    var ignore_tags = /^(pre|textarea)$/i;
    var space_sensitive_tags = /^(a|code|del|pre|s|strike|u)$/i;
    var space_like_tags = /^(br|hr|i|img|pangu)$/i;
    var block_tags = /^(div|h1|h2|h3|h4|h5|h6|p)$/i;

    /*
     1.
     Ӳ�� contentEditable Ԫ�ص� child nodes ߀�Ǖ��� spacing �Ć��}
     ��� contentEditable ��ֵ������ 'true', 'false', 'inherit'
     ����]���@ʽ��ָ�� contentEditable ��ֵ
     һ�㶼���� 'inherit' ������ 'false'

     2.
     ��Ҫ���ض� tag �e�����ּӿո�
     ���� pre

     TODO:
     ̫�����ˣ���ԓ�и��õĽⷨ
     */
    function can_ignore_node(node) {
        var parent_node = node.parentNode;
        while (parent_node.nodeName.search(/^(html|head|body|#document)$/i) === -1) {
            if ((parent_node.getAttribute('contenteditable') === 'true') || (parent_node.getAttribute('g_editable') === 'true')) {
                return true;
            }
            else if (parent_node.nodeName.search(ignore_tags) >= 0) {
                return true;
            }
            else {
                parent_node = parent_node.parentNode;
            }
        }

        return false;
    }

    /*
     nodeType: http://www.w3schools.com/dom/dom_nodetype.asp
     1: ELEMENT_NODE
     3: TEXT_NODE
     8: COMMENT_NODE
     */
    function is_first_text_child(parent_node, target_node) {
        var child_nodes = parent_node.childNodes;

        // ֻ�Д��һ������ text �� node
        for (var i = 0; i < child_nodes.length; i++) {
            var child_node = child_nodes[i];
            if (child_node.nodeType != 8 && child_node.textContent) {
                return child_node === target_node;
            }
        }

        // �]���@ʽ�� return ���� undefined������ if �e��������� false
        // return false;
    }

    function is_last_text_child(parent_node, target_node) {
        var child_nodes = parent_node.childNodes;

        // ֻ�Д൹����һ������ text �� node
        for (var i = child_nodes.length - 1; i > -1; i--) {
            var child_node = child_nodes[i];
            if (child_node.nodeType != 8 && child_node.textContent) {
                return child_node === target_node;
            }
        }

        // return false;
    }

    function insert_space(text) {
        var old_text = text;
        var new_text;

        /*
         Regular Expressions
         https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_Expressions

         Symbols
         ` ~ ! @ # $ % ^ & * ( ) _ - + = [ ] { } \ | ; : ' " < > , . / ?

         3000-303F �����n��̖�͘��c
         3040-309F ����ƽ���� (V)
         30A0-30FF ����Ƭ���� (V)
         3100-312F ע����ĸ (V)
         31C0-31EF �����n�P��
         31F0-31FF ����Ƭ�����Z���Uչ
         3200-32FF ��Ȧ�����n��ĸ���·� (V)
         3400-4DBF �����n�yһ�������֔Uչ A (V)
         4E00-9FFF �����n�yһ�������� (V)
         AC00-D7AF �V������ (�n��)
         F900-FAFF �����n���ݱ������� (V)
         http://unicode-table.com/cn/
         */

        // ǰ��"��"���� >> ǰ�� "��" ����
        text = text.replace(/([\u3040-\u312f\u3200-\u32ff\u3400-\u4dbf\u4e00-\u9fff\uf900-\ufaff])(["'])/ig, '$1 $2');
        text = text.replace(/(["'])([\u3040-\u312f\u3200-\u32ff\u3400-\u4dbf\u4e00-\u9fff\uf900-\ufaff])/ig, '$1 $2');

        // ������F 'ǰ�� " ��" ����' ֮Ĳ����Q����r
        text = text.replace(/(["']+)(\s*)(.+?)(\s*)(["']+)/ig, '$1$3$5');

        // # ��̖��Ҫ�؄e̎��
        text = text.replace(/([\u3040-\u312f\u3200-\u32ff\u3400-\u4dbf\u4e00-\u9fff\uf900-\ufaff])(#(\S+))/ig, '$1 $2');
        text = text.replace(/((\S+)#)([\u3040-\u312f\u3200-\u32ff\u3400-\u4dbf\u4e00-\u9fff\uf900-\ufaff])/ig, '$1 $3');

        // ǰ��<��>���� --> ǰ�� <��> ����
        old_text = text;
        new_text = old_text.replace(/([\u3040-\u312f\u3200-\u32ff\u3400-\u4dbf\u4e00-\u9fff\uf900-\ufaff])([<\[\{\(]+(.*?)[>\]\}\)]+)([\u3040-\u312f\u3200-\u32ff\u3400-\u4dbf\u4e00-\u9fff\uf900-\ufaff])/ig, '$1 $2 $4');
        text = new_text;
        if (old_text === new_text) {
            // ǰ��<���� --> ǰ�� < ����
            text = text.replace(/([\u3040-\u312f\u3200-\u32ff\u3400-\u4dbf\u4e00-\u9fff\uf900-\ufaff])([<>\[\]\{\}\(\)])/ig, '$1 $2');
            text = text.replace(/([<>\[\]\{\}\(\)])([\u3040-\u312f\u3200-\u32ff\u3400-\u4dbf\u4e00-\u9fff\uf900-\ufaff])/ig, '$1 $2');
        }
        // ������F "ǰ�� [ ��] ����" ֮Ĳ����Q����r
        text = text.replace(/([<\[\{\(]+)(\s*)(.+?)(\s*)([>\]\}\)]+)/ig, '$1$3$5');

        // ������ǰ
        text = text.replace(/([\u3040-\u312f\u3200-\u32ff\u3400-\u4dbf\u4e00-\u9fff\uf900-\ufaff])([a-z0-9`@&%=\$\^\*\-\+\|\/\\])/ig, '$1 $2');

        // ��������
        text = text.replace(/([a-z0-9`~!%&=;\|\,\.\:\?\$\^\*\-\+\/\\])([\u3040-\u312f\u3200-\u32ff\u3400-\u4dbf\u4e00-\u9fff\uf900-\ufaff])/ig, '$1 $2');

        return text;
    }

    function spacing(xpath_query) {
        // �Ƿ���˿ո�
        var had_spacing = false;

        /*
         ��� xpath_query �õ��� text()�������@Щ nodes �� text ������ DOM element
         https://developer.mozilla.org/en-US/docs/DOM/document.evaluate
         http://www.w3cschool.cn/dom_xpathresult.html

         snapshotLength Ҫ��� XPathResult.ORDERED_NODE_SNAPSHOT_TYPE ʹ��
         */
        var text_nodes = document.evaluate(xpath_query, document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);

        var nodes_length = text_nodes.snapshotLength;

        var next_text_node;

        // �������桢���e��Ĺ��c�_ʼ
        for (var i = nodes_length - 1; i > -1; --i) {
            var current_text_node = text_nodes.snapshotItem(i);
            // console.log('current_text_node: %O, nextSibling: %O', current_text_node.data, current_text_node.nextSibling);
            // console.log('next_text_node: %O', next_text_node);

            if (can_ignore_node(current_text_node)) {
                next_text_node = current_text_node;
                continue;
            }

            // http://www.w3school.com.cn/xmldom/dom_text.asp
            var new_data = insert_space(current_text_node.data);
            if (current_text_node.data != new_data) {
                had_spacing = true;
                current_text_node.data = new_data;
            }

            // ̎��Ƕ�׵� <tag> �е�����
            if (next_text_node) {
                /*
                 TODO:
                 �F��ֻ�Ǻ��ε��Д���������һ�� node �ǲ��� <br>
                 �fһ����Ƕ�׵Ę˻`�Ͳ�����
                 */
                if (current_text_node.nextSibling) {
                    if (current_text_node.nextSibling.nodeName.search(space_like_tags) >= 0) {
                        next_text_node = current_text_node;
                        continue;
                    }
                }

                // current_text_node ������һ���� + next_text_node �ĵ�һ����
                var text = current_text_node.data.toString().substr(-1) + next_text_node.data.toString().substr(0, 1);
                var new_text = insert_space(text);

                if (text != new_text) {
                    had_spacing = true;

                    /*
                     ������
                     next_node ���� next_text_node �� parent node
                     current_node ���� current_text_node �� parent node
                     */

                    /*
                     ������ next_text_node �� parent node
                     ֱ������ space_sensitive_tags
                     ���� next_text_node ����ǵ�һ�� text child
                     ���ܰѿո���� next_text_node ��ǰ��
                     */
                    var next_node = next_text_node;
                    while (next_node.parentNode &&
                        next_node.nodeName.search(space_sensitive_tags) === -1 &&
                        is_first_text_child(next_node.parentNode, next_node)) {
                        next_node = next_node.parentNode;
                    }
                    // console.log('next_node: %O', next_node);

                    var current_node = current_text_node;
                    while (current_node.parentNode &&
                        current_node.nodeName.search(space_sensitive_tags) === -1 &&
                        is_last_text_child(current_node.parentNode, current_node)) {
                        current_node = current_node.parentNode;
                    }
                    // console.log('current_node: %O, nextSibling: %O', current_node, current_node.nextSibling);

                    if (current_node.nextSibling) {
                        if (current_node.nextSibling.nodeName.search(space_like_tags) >= 0) {
                            next_text_node = current_text_node;
                            continue;
                        }
                    }

                    if (current_node.nodeName.search(block_tags) === -1) {
                        if (next_node.nodeName.search(space_sensitive_tags) === -1) {
                            if ((next_node.nodeName.search(ignore_tags) === -1) && (next_node.nodeName.search(block_tags) === -1)) {
                                if (next_text_node.previousSibling) {
                                    if (next_text_node.previousSibling.nodeName.search(space_like_tags) === -1) {
                                        // console.log('spacing 1-1: %O', next_text_node.data);
                                        next_text_node.data = " " + next_text_node.data;
                                    }
                                }
                                else {
                                    // console.log('spacing 1-2: %O', next_text_node.data);
                                    next_text_node.data = " " + next_text_node.data;
                                }
                            }
                        }
                        else if (current_node.nodeName.search(space_sensitive_tags) === -1) {
                            // console.log('spacing 2: %O', current_text_node.data);
                            current_text_node.data = current_text_node.data + " ";
                        }
                        else {
                            var space_span = document.createElement('pangu');
                            space_span.innerHTML = ' ';

                            // ����һֱ���ӿո�
                            if (next_node.previousSibling) {
                                if (next_node.previousSibling.nodeName.search(space_like_tags) === -1) {
                                    // console.log('spacing 3-1: %O', next_node.parentNode);
                                    next_node.parentNode.insertBefore(space_span, next_node);
                                }
                            }
                            else {
                                // console.log('spacing 3-2: %O', next_node.parentNode);
                                next_node.parentNode.insertBefore(space_span, next_node);
                            }
                        }
                    }
                }
            }

            next_text_node = current_text_node;
        }

        return had_spacing;
    }

    pangu.text_spacing = function(text) {
        return insert_space(text);
    };

    pangu.page_spacing = function() {
        // var p = 'page_spacing';
        // console.profile(p);
        // console.time(p);
        // var start = new Date().getTime();

        /*
         // >> ����λ�õĹ��c
         . >> ��ǰ���c
         .. >> �����c
         [] >> �l��
         text() >> ���c�����փ��ݣ����� hello ֮� <tag>hello</tag>

         [@contenteditable]
         ���� contenteditable ���ԵĹ��c

         normalize-space(.)
         ��ǰ���c���^β�Ŀհ���Ԫ�������Ƴ�����춃ɂ����ϵĿհ���Ԫ�����ÓQ�Ɇ�һ�հ�
         https://developer.mozilla.org/en-US/docs/XPath/Functions/normalize-space

         name(..)
         �����c�����Q
         https://developer.mozilla.org/en-US/docs/XPath/Functions/name

         translate(string, "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "abcdefghijklmnopqrstuvwxyz")
         �� string �D�Q��С������� XML �� case-sensitive ��
         https://developer.mozilla.org/en-US/docs/XPath/Functions/translate

         1. ̎�� <title>
         2. ̎�� <body> ���µĹ��c
         3. ���^ contentEditable �Ĺ��c
         4. ���^�ض����c������ <script> �� <style>

         ע�⣬���µ� query ֻ��ȡ�������c�� text ���ݣ�
         */
        var title_query = '/html/head/title/text()';
        spacing(title_query);

        // var body_query = '/html/body//*[not(@contenteditable)]/text()[normalize-space(.)]';
        var body_query = '/html/body//*/text()[normalize-space(.)]';
        ['script', 'style', 'textarea'].forEach(function(tag) {
            /*
             ��Փ���@�ׂ� tag �e�治���������� tag
             ���Կ���ֱ���� .. ȡ�����c

             ex: [translate(name(..), "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "abcdefghijklmnopqrstuvwxyz") != "script"]
             */
            body_query += '[translate(name(..),"ABCDEFGHIJKLMNOPQRSTUVWXYZ","abcdefghijklmnopqrstuvwxyz")!="' + tag + '"]';
        });
        var had_spacing = spacing(body_query);

        // console.profileEnd(p);
        // console.timeEnd(p);
        // var end = new Date().getTime();
        // console.log(end - start);

        return had_spacing;
    };

    pangu.element_spacing = function(selector_string) {
        var xpath_query;

        if (selector_string.indexOf('#') === 0) {
            var target_id = selector_string.substr(1, selector_string.length - 1);

            // ex: id("id_name")//text()
            xpath_query = 'id("' + target_id + '")//text()';
        }
        else if (selector_string.indexOf('.') === 0) {
            var target_class = selector_string.slice(1);

            // ex: //*[contains(@class, "target_class")]//*/text()
            xpath_query = '//*[contains(@class, "' + target_class + '")]//*/text()';
        }
        else {
            var target_tag = selector_string;

            // ex: //tag_name/text()
            xpath_query = '//' + target_tag + '//text()';
        }

        var had_spacing = spacing(xpath_query);

        return had_spacing;
    };

}(window.pangu = window.pangu || {}));