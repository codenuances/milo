/*
 * Copyright (c) 2017 Kevin Herron
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 *
 * The Eclipse Public License is available at
 *   http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 *   http://www.eclipse.org/org/documents/edl-v10.html.
 */

package org.eclipse.milo.opcua.stack.core.types.structured;

import com.google.common.base.MoreObjects;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.UaSerializationException;
import org.eclipse.milo.opcua.stack.core.serialization.UaDecoder;
import org.eclipse.milo.opcua.stack.core.serialization.UaEncoder;
import org.eclipse.milo.opcua.stack.core.serialization.codecs.BuiltinDataTypeCodec;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.QualifiedName;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.enumerated.NodeClass;

public class ObjectTypeNode extends TypeNode {

    public static final NodeId TypeId = Identifiers.ObjectTypeNode;
    public static final NodeId BinaryEncodingId = Identifiers.ObjectTypeNode_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.ObjectTypeNode_Encoding_DefaultXml;

    protected final Boolean isAbstract;

    public ObjectTypeNode() {
        super(null, null, null, null, null, null, null, null);
        this.isAbstract = null;
    }

    public ObjectTypeNode(NodeId nodeId, NodeClass nodeClass, QualifiedName browseName, LocalizedText displayName, LocalizedText description, UInteger writeMask, UInteger userWriteMask, ReferenceNode[] references, Boolean isAbstract) {
        super(nodeId, nodeClass, browseName, displayName, description, writeMask, userWriteMask, references);
        this.isAbstract = isAbstract;
    }

    public Boolean getIsAbstract() { return isAbstract; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("NodeId", nodeId)
            .add("NodeClass", nodeClass)
            .add("BrowseName", browseName)
            .add("DisplayName", displayName)
            .add("Description", description)
            .add("WriteMask", writeMask)
            .add("UserWriteMask", userWriteMask)
            .add("References", references)
            .add("IsAbstract", isAbstract)
            .toString();
    }

    public static class Codec extends BuiltinDataTypeCodec<ObjectTypeNode> {

        @Override
        public Class<ObjectTypeNode> getType() {
            return ObjectTypeNode.class;
        }

        @Override
        public ObjectTypeNode decode(UaDecoder decoder) throws UaSerializationException {
            NodeId nodeId = decoder.readNodeId("NodeId");
            NodeClass nodeClass = NodeClass.from(decoder.readInt32("NodeClass"));
            QualifiedName browseName = decoder.readQualifiedName("BrowseName");
            LocalizedText displayName = decoder.readLocalizedText("DisplayName");
            LocalizedText description = decoder.readLocalizedText("Description");
            UInteger writeMask = decoder.readUInt32("WriteMask");
            UInteger userWriteMask = decoder.readUInt32("UserWriteMask");
            ReferenceNode[] references =
                decoder.readBuiltinStructArray(
                    "References",
                    ReferenceNode.class
                );
            Boolean isAbstract = decoder.readBoolean("IsAbstract");

            return new ObjectTypeNode(nodeId, nodeClass, browseName, displayName, description, writeMask, userWriteMask, references, isAbstract);
        }

        @Override
        public void encode(ObjectTypeNode value, UaEncoder encoder) throws UaSerializationException {
            encoder.writeNodeId("NodeId", value.nodeId);
            encoder.writeInt32("NodeClass", value.nodeClass != null ? value.nodeClass.getValue() : 0);
            encoder.writeQualifiedName("BrowseName", value.browseName);
            encoder.writeLocalizedText("DisplayName", value.displayName);
            encoder.writeLocalizedText("Description", value.description);
            encoder.writeUInt32("WriteMask", value.writeMask);
            encoder.writeUInt32("UserWriteMask", value.userWriteMask);
            encoder.writeBuiltinStructArray(
                "References",
                value.references,
                ReferenceNode.class
            );
            encoder.writeBoolean("IsAbstract", value.isAbstract);
        }
    }

}
